package com.inspur.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.util.List;
import java.util.Properties;

import org.apache.commons.dbutils.ResultSetHandler;


/**
 * @class hiveTools
 * @author liukai
 *
 */
public class hiveTools {
	private  String driverName;
	private  String url;
	private  String user;
	private  String password;
	
	
	/**
	 * @method hiveTools
	 * @description hiveTools�๹�캯��
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
	 * @method releaseConn
	 * @param conn
	 * @throws SQLException
	 */
	public void releaseConn(Connection conn) throws SQLException{
		// close the conn
		conn.close(); 
	}


	/**
	 * @method useDatabase
	 * @description 
	 * @author:LiuKai 
	 * @param conn
	 * @param databaseName
	 * @throws SQLException
	 */
	public  void useDatabase(Connection conn,String databaseName) throws SQLException{
		Statement stmt = null;
		ResultSet res = null;
		
		System.out.println("USE TABLE: "+databaseName);
		String sql  = "use "+databaseName;
		stmt = conn.createStatement();
		stmt.executeQuery(sql);

		// show tables		
		res = stmt.executeQuery("show tables");
		// release(note:release res first)
		res.close();
		stmt.close();	
	}
	

	/**
	 * @method funUpdate
	 * @description 
	 * @author:LiuKai
	 * @param conn
	 * @param sql SQL���
	 * @throws SQLException
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
	
	
	public void fun1(Connection conn, String sql ) throws SQLException{
		Statement stmt = null;
		stmt = conn.createStatement();	 
		stmt.executeQuery(sql); 	
	}
	
	public void HDFSuploadHive(Connection conn,String fileName,String tableName,
			String partitionName,String tableFormat) throws SQLException{
		//create table 
//		System.out.println(">>RUNING: create table");
		String sql = "create table if not exists " + tableName + " " + tableFormat + 
				"PARTITIONED BY(day string)" +
				"row format delimited fields terminated by ','";
		funUpdate(conn, sql);
		// create partition
//		System.out.println(">>RUNING: create partition");
		sql = "ALTER TABLE "+tableName+" ADD IF NOT EXISTS partition (day =\""
		         +partitionName+"\")";
		funUpdate(conn, sql);
		// upload hdfs file to hive
		//String HDFSfilefolder = "hdfs://Master:9000/user/hadoop/tmp/";
		String HDFSfilePath = fileName;
		sql = "load data inpath '" + HDFSfilePath +"' into table "+ tableName + " " + 
				"partition (day =\'"+partitionName+"\')"; 
//		System.out.println(sql);
		funUpdate(conn, sql);
		
	}
	
	

    /**
     * @method query
     * @description ��ݲ�ѯ
     * @author LiuKai
     * @param conn
     * @param sql
     * @param params
     * @param rsh ResultSetHandler��
     * @return Object
     * @throws SQLException
     */
	public Object query(Connection conn, String sql, String params[],ResultSetHandler<?> rsh) throws SQLException{
		PreparedStatement pstm = null;
		ResultSet res = null;
		
		System.out.println(">>RUNING: query");
		long sysDateStart = System.currentTimeMillis(); //��ʼ��ʱ 		   		 
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
}
