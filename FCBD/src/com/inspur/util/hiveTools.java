package com.inspur.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import org.apache.commons.dbutils.ResultSetHandler;


public class hiveTools {

	private  String driverName;
	private  String url;
	private  String user;
	private  String password;
	
	
	/**
	 * @author LiuKai
	 */
	public hiveTools(){
			Properties prop = new Properties();
			InputStream in = hiveJdbcTest.class.getClassLoader().getResourceAsStream("Hivedb.properties");
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
	 * @description:��ȡ��ݿ����Ӷ���
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
	 * @method useDatabase
	 * @description ʹ��ĳһ����ݿ�
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
		int i =0;
		System.out.println("TABLE LIST OF "+ databaseName + " IS");
		while(res.next()) {
			System.out.println("Table "+i+" :" + res.getString(1) );
			i++;
		}
		// release
		stmt.close();
		res.close();
	}
	

	/**
	 * @method funUpdate
	 * @description ��ݿ���º���
	 * @author:LiuKai
	 * @param conn
	 * @param sql SQL���
	 * @throws SQLException
	 */
	public void funUpdate(Connection conn, String sql ) throws SQLException{
		Statement stmt = null;
		stmt = conn.createStatement();	 
		stmt.executeQuery(sql); 
		// release
		stmt.close();
	}


	/**
	 * @method query
	 * @description ��ݲ�ѯ
	 * @author:LiuKai
	 * @param conn
	 * @param tableName
	 * @param condition
	 * @throws SQLException 
	 */
	public Object query(Connection conn, String sql, String params[],ResultSetHandler<?> rsh) throws SQLException{
		PreparedStatement pstm = null;
		ResultSet res = null;
		
		System.out.println("RUNING: query");
		long sysDateStart = System.currentTimeMillis(); 
		pstm = conn.prepareStatement(sql);
		for(int i=0;i<params.length;i++){
			//System.out.println("i="+i);
			pstm.setString(i+1, params[i]);
		}
		res = pstm.executeQuery();
		
		
		// convert res to list<string[]>		
//		while (res.next()) {
//			System.out.println(res.getInt(1) + "\t" + res.getString(2));
//		}
		long sysDateStop = System.currentTimeMillis(); 
		long costTime = sysDateStop - sysDateStart; 
		System.out.println("----Time for query is "+costTime + " ms.----");
		Object result = rsh.handle(res);
		// release
		res.close();
		pstm.close();
		conn.close();
		
		return result;
		
	}
	
}
