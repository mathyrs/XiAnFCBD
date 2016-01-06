package com.inspur.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;

/**
 * @class hiveTools
 * @author LiuKai
 */
public class hiveTools {
	private  String driverName;
	private  String url;
	private  String user;
	private  String password;
	

	/**
	 * @Title hiveTools
	 * @Decription hiveTools class construction function
	 * @author LiuKai
	 */
	public hiveTools(){
			Properties prop = new Properties();
			InputStream in = hiveTools.class.getClassLoader().getResourceAsStream("Hivedb.properties");
			try {
				prop.load(in);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.driverName = prop.getProperty("driverName");
			this.url = prop.getProperty("url");
			this.user = prop.getProperty("user");
			this.password = prop.getProperty("password");
	}
	
	/**
	 * @method hiveTools
	 * @description hiveTools construction function
	 * @author LiuKai
	 * @param driverName
	 * @param url
	 * @param user
	 * @param password
	 * @throws SQLException
	 */
	public hiveTools(String driverName,String url,String user, String password) throws SQLException{	
		this.driverName = driverName;
		this.url = url;
		this.user = user;
		this.password = password;
	}

	/**
	 * @method:getConnection
	 * @description:
	 * @author:LiuKai 
	 * @return:Connection
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public  Connection getConnection( ){
		Connection conn;
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, user, password);
			return conn; 
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	* @Title: releaseConn 
	* @Description: release the conn 
	* @param @param conn
	* @param @throws SQLException  
	* @return void 
	* @throws
	 */
	public void releaseConn(Connection conn) throws SQLException{
		// close the connect
		conn.close(); 
	}


	/**
	 * 
	* @Title: useDatabase 
	* @Description: choose hive database 
	* @param @param conn
	* @param @param databaseName
	* @param @throws SQLException  
	* @return void 
	* @throws
	 */
	public  void useDatabase(Connection conn,String databaseName) throws SQLException{
		Statement stmt = null;
		
		String sql  = "use "+databaseName;
		stmt = conn.createStatement();
		stmt.executeQuery(sql);
		
		// release
		stmt.close();	
	}
	

	/**
	 * 
	* @Title: funUpdate 
	* @Description: common function for non-answer hive operation 
	* @param @param conn
	* @param @param sql
	* @param @throws SQLException  
	* @return void 
	* @throws
	 */
	public void funUpdate(Connection conn, String sql ) throws SQLException{
		Statement stmt = null;
		ResultSet res = null;
		stmt = conn.createStatement();	
		res = stmt.executeQuery(sql);
		while(res.next()) {
			System.out.println(res.getString(1) );
		}	
		// release
		stmt.close();
	}
	
	/**
	* @Title: HDFSuploadHive 
	* @Description: upload file from HDFS to Hive 
	* @param @param conn
	* @param @param HDFSfilePath
	* @param @param tableName
	* @param @param partitionName
	* @param @param tableFormat
	* @param @throws SQLException  
	* @return void 
	* @throws
	 */
	public void HDFSuploadHive(Connection conn,String HDFSfilePath,String tableName,
			String partitionName,String tableFormat) throws SQLException{	
		String HiveSQL = null;
		//create table if necessary
		HiveSQL = "create table if not exists " + tableName + " " + tableFormat + 
				"PARTITIONED BY(day string)" +
				"row format delimited fields terminated by ','";
		funUpdate(conn, HiveSQL);

		// create partition if necessary
		HiveSQL = "ALTER TABLE "+tableName+" ADD IF NOT EXISTS partition (day =\""+partitionName+"\")";
		funUpdate(conn, HiveSQL);

		// load date from HDFS to Hive
		HiveSQL = "load data inpath '" + HDFSfilePath +"' into table "+ tableName 
				+ " " + "partition (day =\'"+partitionName+"\')"; 
		funUpdate(conn, HiveSQL);	
	}
	

	/**
	 * 
	 * @Title: query 
	 * @Description: make hive query 
	 * @param @param conn
	 * @param @param sql
	 * @param @param params
	 * @param @param rsh
	 * @param @return
	 * @param @throws SQLException  
	 * @return Object 
	 * @throws
	 */
	public Object query(Connection conn, String sql, String params[],ResultSetHandler<?> rsh) throws SQLException{
		PreparedStatement pstm = null;
		ResultSet res = null;
		
		System.out.println(">>RUNING: query");
		long sysDateStart = System.currentTimeMillis(); 		   		 
		pstm = conn.prepareStatement(sql);
		
		for(int i=0;i<params.length;i++){
			pstm.setString(i+1, params[i]);
		}
		res = pstm.executeQuery();
		Object result = rsh.handle(res);
		
		// release(note:release res first)
		res.close();
		pstm.close();
		
		long sysDateStop = System.currentTimeMillis(); 
		long costTime = sysDateStop - sysDateStart; 
		System.out.println("----Time for query is "+costTime + " ms.----");	
		
		return result;	
	}
	
	/**
	 * 
	* @Title: query2 
	* @Description: TODO 
	* @param @param conn
	* @param @param tmpTableName
	* @param @param tableFormat
	* @param @param sql
	* @param @param params
	* @param @param rsh
	* @param @return
	* @param @throws SQLException  
	* @return Object 
	* @throws
	 */
	public Object query2(Connection conn,String tmpTableName,String tableFormat,
			   String sql, String params[],ResultSetHandler<?> rsh) throws SQLException{
		
		// step 1:check if temp table 'tmp' exist
		String creatSql = "create table if not exists " + tmpTableName + " " + tableFormat + 
				"row format delimited fields terminated by ','";
		funUpdate(conn, creatSql);
		
	    // step 2:make query and save the result into table tmp;
		PreparedStatement pstm = null;
		ResultSet res = null;
		pstm = conn.prepareStatement(sql);
		
		for(int i=0;i<params.length;i++){
			pstm.setString(i+1, params[i]);
		}
		res = pstm.executeQuery();
		rsh.handle(res);
		
		// release(note:release res first)
		res.close();
		pstm.close();
		
		
		// step 3:get tmp table 
		String Hivesql = "SELECT * FROM "+ tmpTableName;
		String params2[] = {};
		@SuppressWarnings("unchecked")
		List<String[]> result = (List<String[]>) query(conn, Hivesql, params2, new ArrayListHandler());
		
		return result;		
	}
}
