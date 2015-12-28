package com.inspur.util;

import java.io.File;
import java.io.IOException;
//import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.dbutils.handlers.ArrayListHandler;



public class hiveJdbcTest {
	private static String driverName = "org.apache.hadoop.hive.jdbc.HiveDriver";
	private static String url = "jdbc:hive://192.168.1.3:10000/default";
	private static String user = "hadoop";
	private static String password = "";
	
	public static void main(String[] args) throws SQLException, IOException{
		hiveJdbcTest m_hiveJdbctest = new hiveJdbcTest();
		m_hiveJdbctest.simpleTest();
	}
	
	/**
	 * @method simpleTest
	 * @description simple test for java-jdbc-hive 
	 * @author liukai
	 * @throws SQLException
	 * @throws IOException 
	 */
	public void simpleTest() throws SQLException, IOException{
		String sql = "";
		String databaseName = "default";
		String tableName = "xp";
		String filepath1 = "/home/hadoop/2011-09-10_FJ10C_64_02.txt";
		String filepath2 = "/home/hadoop/2011-09-10_FJ10C_64_03.txt";
		//String outputfilename = "/home/hadoop/2011-09-10_FJ10C_64_02_output";
		String tableFormat = " (ID int, TIME string, a int, b int, name string, value double) ";
		
		//hiveTools mhivetool = new hiveTools(driverName,url,user,password);
		hiveTools mhivetool = new hiveTools();
		Connection conn = mhivetool.getConnection();
		mhivetool.useDatabase(conn, databaseName); 

		//create table
		System.out.println(">>RUNING: create table");
		//sql = "create table if not exists " + tableName + " " + tableFormat + "  row format delimited fields terminated by ','  " ;
		sql = "create table if not exists " + tableName + " " + tableFormat + 
				"PARTITIONED BY(day string)" +
				"row format delimited fields terminated by ','";
		mhivetool.funUpdate(conn, sql);
		System.out.println("create table done"); 
		
		// create partition A
		String partitionStr1 = "20150101";
		sql = "ALTER TABLE "+tableName+" ADD IF NOT EXISTS partition (day =\""+partitionStr1+"\")";
		System.out.println(sql);
		mhivetool.funUpdate(conn, sql);
		
		// create partition B
		String partitionStr2 = "20150102";
		sql = "ALTER TABLE "+tableName+" ADD IF NOT EXISTS partition (day =\'"+partitionStr2+"\')";
		mhivetool.funUpdate(conn, sql);
		
		
		//load data
		System.out.println(">>RUNING: load data");
		//sql = "load data local inpath '" + filepath +"' overwrite into table "+ tableName; 
		sql = "load data local inpath '" + filepath1 +"' into table "+ tableName + " " + 
				"partition (day =\'"+partitionStr1+"\')"; 
		mhivetool.funUpdate(conn, sql);
		
		sql = "load data local inpath '" + filepath2 +"' into table "+ tableName + " " + 
				"partition (day =\'"+partitionStr2+"\')"; 
		mhivetool.funUpdate(conn, sql);
		System.out.println("load data done"); 
		
		
		// make query	
		String params2[] = {};
		String params3[] = {"1"};
		String params4[] = {"1","p20150101"};
		tableName = "t111212015010120150130";
		String partionName = "p20150101";
		String flightName = "FJ-10A";
		String Hivesql = "select * from " + tableName + 
				   " where day='"+partionName +"'" +" AND flight ='"+flightName+"'";
		//+" AND flight ='"+flightName+"'"
		System.out.println(Hivesql);
		@SuppressWarnings("unchecked")
		List<Object[]> result = (List) mhivetool.query(conn, Hivesql, params2,new ArrayListHandler());
		System.out.println("query done"); 
		System.out.println("");
		
		for(Object[] aa:result){
			for (Object temp:aa)
			System.out.print(temp + "     ");
			System.out.println();
		}
//		while (result.next()) {
//			System.out.println(res.getInt(1) + "\t" + res.getString(2));
//		}
		//mhivetool.query(conn, "select * from "+tableName+ " where id = ? AND day='20150101' ", params3);
		
		
		
	}
	
	/**
	 * @method complexTest
	 * @description complex test for java-jdbc-hive 
	 * @author liukai
	 * @throws SQLException
	 */
	public void complexTest() throws SQLException{
		String sql = "";
		String databaseName = "default";
		String tableName = "liukai1";
		String filepath = null;
		String filefolder = "E:\\simulatedData";
		String tableFormat = "(ID int, TIME string, a int, b int, name string, value double) ";
			
		hiveTools mhivetool = new hiveTools(driverName,url,user,password);
		Connection conn = mhivetool.getConnection();
		mhivetool.useDatabase(conn, databaseName); 
		
		boolean fload = false;
		boolean fquery = true;
		if(fload){
			//create table
			System.out.println(">>RUNING: create table");
			sql = "create table if not exists " + tableName + " " + tableFormat + 
					"PARTITIONED BY(recordDate string)" +
					"row format delimited fields terminated by ','";
			System.out.println(sql);
			mhivetool.funUpdate(conn, sql);

			// get filename list
			System.out.println(">>RUNING: get filename list");
			File file = new File(filefolder);
			System.out.println(file.getAbsolutePath());
			File[] fileNameArray = file.listFiles();
			System.out.println(fileNameArray.length);
			
			// load file
			System.out.println(">>RUNING: load file");
			long sysDateStart = System.currentTimeMillis(); //��ʼ��ʱ 	
			for(File fielname:fileNameArray){
				String filenamestr = fielname.getName();
				filepath = "/home/hadoop/simulatedData/" + filenamestr;
				String partitionStr = filenamestr.substring(0, 10);
				//System.out.println(partitionStr);

				//create partition
				//System.out.println(">>RUNING: create partition");
				sql = "ALTER TABLE "+tableName+" ADD IF NOT EXISTS partition (recordDate =\'"+partitionStr+"\')";
				//System.out.println(sql);
				mhivetool.funUpdate(conn, sql);

				//System.out.println(">>RUNING: load data");
				sql = "load data local inpath '" + filepath +"' into table "+ tableName + " " + 
						"partition (recordDate =\'"+partitionStr+"\')"; 
				System.out.println(sql);
				mhivetool.funUpdate(conn, sql);

			}
			long sysDateStop = System.currentTimeMillis(); 
			long costTime = sysDateStop - sysDateStart; 
			System.out.println("----Time for load is "+costTime + " ms.----");
		}
		
		if(fquery){
			String params3[] = {"1"};
			//mhivetool.query(conn, "select * from "+tableName+ " where id = ? AND recordDate = '2015-01-01'", params3);
			System.out.println("query done"); 
			System.out.println("");
		}
	} 

}





