/**
 * version: 0.1
 */

package com.inspur.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class hiveTools {
	private  String driverName;
	private  String url;
	private  String user;
	private  String password;
	
	public hiveTools(String driverName,String url,String user, String password) throws SQLException{	
		this.driverName = driverName;
		this.url = url;
		this.user = user;
		this.password = password;
	}

	/**
	 * @method:getConnection
	 * @description:获取数据库连接对象
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
	 * @description 使用某一个数据库
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
	 * @description 数据库更新函数
	 * @param conn
	 * @param sql SQL语句
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
	 * @description 数据查询
	 * @param conn
	 * @param tableName
	 * @param condition
	 * @throws SQLException 
	 */
	public  void query(Connection conn, String sql, String params[]) throws SQLException{
		PreparedStatement pstm = null;
		ResultSet res = null;
		
		System.out.println("RUNING: query");
		long sysDateStart = System.currentTimeMillis(); //开始计时 		   		 
		pstm = conn.prepareStatement(sql);
		for(int i=0;i<params.length;i++){
			System.out.println("i="+i);
			pstm.setString(i+1, params[i]);
		}
		res = pstm.executeQuery();	 
		while (res.next()) {
			System.out.println(res.getInt(1) + "\t" + res.getString(2));
		}
		long sysDateStop = System.currentTimeMillis(); 
		long costTime = sysDateStop - sysDateStart; 
		System.out.println("----Time for query is "+costTime + " ms.----");
		// release
		pstm.close();
		res.close();
	}


	/**
	 * @method exportData
	 * @description 数据导出
	 * @author Liukai
	 * @param conn
	 * @param tableName
	 * @param filepath
	 * @throws SQLException
	 */
//	public  void exportData(Connection conn, String sql) throws SQLException{
//		ResultSet res = null;
//		
//		System.out.println("RUNNING: export data");
//		Statement stmt = conn.createStatement();
//		res = stmt.executeQuery(sql);
//		//System.out.println("running export data done");
//		res.close();
//	} 
}
