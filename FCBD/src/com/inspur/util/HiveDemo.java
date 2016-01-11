package com.inspur.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.List;
import org.apache.commons.dbutils.handlers.ArrayListHandler;

import com.inspur.util.hiveTools;
import com.inspur.util.resultSetContentOpera;





public class HiveDemo {	
	
	/*********************
	 * demoDataQuery2: the demo for date query in hadoop system
	 *******************/
	
	public static void main(String[] args) throws SQLException, IOException{
		HiveDemo m_hiveJdbctest = new HiveDemo();
		m_hiveJdbctest.demoQueryForResultSet();
	}
	
	public void demoQueryForResultSet() throws SQLException{
		String databaseName = "default";
		hiveTools mhivetool = new hiveTools();
		Connection conn = mhivetool.getConnection();
		mhivetool.useDatabase(conn, databaseName);
		
		// make query and get ResultSet res
		String tmpTableName = "tmp";
		String tableFormat = 
				" (ID int, TIME string, a int, b int, name string, value double, fliget string) ";
		String params2[] = {};
		String Hivesql = "FROM t1112220152020 se " +
				"INSERT OVERWRITE TABLE tmp " +
				"SELECT id,time,a,b,name,value,flight " +
				"WHERE se.day = 'p20150101'";
				//"WHERE se.day >= 'p20131102' AND se.day <= 'p20150120' AND se.name != 'XXX'";
		resultSetContentOpera res =  mhivetool.queryForResultSet(conn, tmpTableName,tableFormat,
				Hivesql, params2,new ArrayListHandler());
			
	}
		
	
	/**
	 * 
	* @Title: demoDataQuery2 
	* @Description: the demo for query2 in hiveTools 
	* @param @throws SQLException  
	* @return void 
	* @throws
	 */
	public void demoDataQuery2() throws SQLException{
		String databaseName = "default";
		hiveTools mhivetool = new hiveTools();
		Connection conn = mhivetool.getConnection();
		mhivetool.useDatabase(conn, databaseName); 
		
		long sysDateStart = System.currentTimeMillis(); // time for start
		
		
		String tmpTableName = "tmp";
		String tableFormat = 
				" (ID int, TIME string, a int, b int, name string, value double, fliget string) ";
		String params2[] = {};
		String Hivesql = "FROM t1112220152020 se " +
				"INSERT OVERWRITE TABLE tmp " +
				"SELECT id,time,a,b,name,value,flight " +
				"WHERE se.day = 'p20150101'";
				//"WHERE se.day >= 'p20131102' AND se.day <= 'p20150120' AND se.name != 'XXX'";
		@SuppressWarnings("unchecked")
		List<String[]> result = (List<String[]>) mhivetool.query2(conn, tmpTableName,tableFormat,
				Hivesql, params2,new ArrayListHandler());
		
		long sysDateStop = System.currentTimeMillis();  // time for stop
		long costTime = sysDateStop - sysDateStart; 
		System.out.println("----total Time for  query is "+costTime + " ms.----");	
		System.out.println("query done");
		
		
//		for(Object[] aa:result){
//			for (Object temp:aa){
//				System.out.print(temp +"   ");
//			}
//			System.out.println();
//		}
		mhivetool.releaseConn(conn); 
		
	}
	
	
	/**
	 * 
	* @Title: demoForQuery 
	* @Description: the demo for query in hiveTools 
	* @param @throws SQLException  
	* @return void 
	* @throws
	 */
	public void demoDataQuery() throws SQLException{
		String databaseName = "default";
		hiveTools mhivetool = new hiveTools();
		Connection conn = mhivetool.getConnection();
		mhivetool.useDatabase(conn, databaseName); 
		
		
		long sysDateStart = System.currentTimeMillis(); // time for start
		String params2[] = {};
		String Hivesql = "SELECT * FROM t1112120152020 WHERE day > 'p20131102' AND day < 'p20150102' AND name != 'XXX'";
		System.out.println("HiveQL: "+Hivesql);
		@SuppressWarnings("unchecked")
		List<String[]> result = (List<String[]>) mhivetool.query(conn, Hivesql, params2,
				                                                     new ArrayListHandler());
		long sysDateStop = System.currentTimeMillis();  // time for stop
		long costTime = sysDateStop - sysDateStart; 
		System.out.println("----Time for query is "+costTime + " ms.----");	
		System.out.println("query done"); 

		for(Object[] aa:result){
			for (Object temp:aa){
				System.out.print(temp +"   ");
			}
			System.out.println();
		}
		mhivetool.releaseConn(conn); 	
	}
	
	/**
	 * @method clearHiveTable
	 * @description clear the Hive table 
	 * @author LiuKai
	 * @throws SQLException 
	 * @note we first get the table list by "show tables" sentence, 
	 *       and then drop them one by one
	 */
	public void clearHiveTable() throws SQLException{
		String databaseName = "default";
		hiveTools mhivetool = new hiveTools();
		Connection conn = mhivetool.getConnection();
		mhivetool.useDatabase(conn, databaseName); 
		
		// make sure some hive operation can be made in local model
		String sql1 = "set hive.exec.mode.local.auto=true;";
		mhivetool.funUpdate(conn, sql1);
		
		// get the table list named by 't.*' and drop each of them
		String params[] = {};
		String Hivesql = "show tables \'t.*\'";
		@SuppressWarnings("unchecked")
		List<Object[]> result = (List<Object[]>) mhivetool.query(conn, Hivesql, params,
                new ArrayListHandler());
		
		// drop table
		for(Object[] aa:result){
			for (Object temp:aa){
				String Hivesql2 = "drop table " + temp;
				System.out.println(Hivesql2);	
				mhivetool.funUpdate(conn, Hivesql2);
			}
		}	
	}

	
	/**
	 * 
	* @Title: local2Hive 
	* @Description: TODO 
	* @param @throws SQLException  
	* @return void 
	* @throws
	 */
	public void local2Hive() throws SQLException{
		String databaseName = "default";
		hiveTools mhivetool = new hiveTools();
		Connection conn = mhivetool.getConnection();
		mhivetool.useDatabase(conn, databaseName); 
			
		String folder = "/home/hadoop/simulatedData/";
		String fileName = "11121_20150101_FJ10A.txt";
		String tmpStr[] = fileName.split("_");
		String tableName = "t"+ tmpStr[0];
		String partitionName = "p"+tmpStr[1];
		String filepath1 = folder+fileName;
		String tableFormat = 
				" (ID int, TIME string, a int, b int, name string, value double, fliget string) ";

		//create table
		System.out.println(">>RUNING: create table");
		String sql = "create table if not exists " + tableName + " " + tableFormat + 
				"PARTITIONED BY(day string)" +
				"row format delimited fields terminated by ','";
		mhivetool.funUpdate(conn, sql);
		
		// create partition 
		sql = "ALTER TABLE "+tableName+" ADD IF NOT EXISTS partition (day =\""+partitionName+"\")";
		mhivetool.funUpdate(conn, sql);
		
		// load date
		System.out.println(">>RUNING: upload Local 2 Hive");
		String hiveQL = "load data local inpath '" + filepath1 +"' into table "+ tableName 
				+ " " + "partition (day =\'"+partitionName+"\')"; 
		System.out.println(hiveQL);
		mhivetool.funUpdate(conn, hiveQL);
		
		
		// make query	
		String params2[] = {};
		String Hivesql = "select * from " + tableName + " where day='"+partitionName +"'";
		@SuppressWarnings("unchecked")
		List<Object[]> result = (List<Object[]>) mhivetool.query(conn, Hivesql, params2,new ArrayListHandler());
		System.out.println("query done"); 
		System.out.println("");
		
		for(Object[] aa:result){
			for (Object temp:aa)
			System.out.print(temp + "     ");
			System.out.println();
		}
	}
}
