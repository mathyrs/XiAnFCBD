package com.inspur.util;

import java.sql.Connection;
import java.sql.SQLException;

public class hiveTest {
	
	private static String driverName = "org.apache.hadoop.hive.jdbc.HiveDriver";
	private static String url = "jdbc:hive://192.168.1.3:10000/default";
	private static String user = "hadoop";
	private static String password = "";
	
	public static void main(String[] args) throws SQLException{
		String sql = "";
		String databaseName = "default";
		String tableName = "liukai";
		String filepath = "/home/hadoop/2011-09-10_FJ10C_64_02.txt";
		String tableFormat = " (ID int, TIME string, a int, b int, name string, value double) ";
			
		hiveTools mhivetool = new hiveTools(driverName,url,user,password);
		//get connection
		Connection conn = mhivetool.getConnection();
		//use the database
		mhivetool.useDatabase(conn, databaseName); 

		//create table
		System.out.println("RUNING: create table");
		sql = "create table if not exists " + tableName + " " + tableFormat + "  row format delimited fields terminated by ','  " ;
		mhivetool.funUpdate(conn, sql);
		System.out.println("create table done"); 
		System.out.println("");
		
		//load data
		System.out.println("RUNING: load data");
		sql = "load data local inpath '" + filepath +"' overwrite into table "+ tableName; 
		mhivetool.funUpdate(conn, sql);
		System.out.println("load data done"); 
		System.out.println("");
		
		// make query	
		String params3[] = {"1"};
		mhivetool.query(conn, "select * from "+tableName+ " where id = ?", params3);
		System.out.println("query done"); 
		System.out.println("");
		
		// export data
		System.out.println("RUNING: export data");
		sql = "insert overwrite local directory '" + filepath + "' select * from " + tableName;
		mhivetool.funUpdate(conn, sql);
		System.out.println("export done");

	}

}
