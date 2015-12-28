package com.inspur.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.handlers.ArrayListHandler;

import com.inspur.util.HDFSTools;
import com.inspur.util.hiveTools;

/** 
 * @author LiuKai 
 * @version  
 */
public class HDFSHiveDemo {
	public static void main(String [] args) throws IOException, URISyntaxException, SQLException
	{
		
    	HDFSTools mhdfs = new HDFSTools();
    	String SourfileName = "11121_20150405_FJ-10D.txt";
    	String fileName = SourfileName;
    	String tableName = "t111212015010120150130";
    	String partitionName = "p20150405";
    	
    	
    	
    	// write file from local path to HDFS:user/hadoop/tmp/
    	String SourfileFolder = "D:/"; 
		String Objfilefolder = "hdfs://Master:9000/user/hadoop/tmp/";
		String Sourfile = SourfileFolder+SourfileName;
    	String Objfile = Objfilefolder+SourfileName;
    	
    	boolean bWrite = true;
    	if(bWrite){			
    		mhdfs.WriteToHDFS(Objfile, Sourfile);
    	}	


		String databaseName = "default";
		hiveTools mhivetool = new hiveTools();
		Connection conn = mhivetool.getConnection();
		mhivetool.useDatabase(conn, databaseName); 
    	
		// upload file from HDFS to Hive
    	boolean funUpload = true;
    	if(funUpload){
    		
    		String tableFormat = 
    				   " (ID int, TIME string, a int, b int, name string, value double) ";
    		mhivetool.HDFSuploadHive(conn,Objfile,tableName,partitionName,tableFormat);
    		System.out.println("UpLoad over!");
    	}
    	
    	//clear HDFS
    	boolean bDelet = true;
    	if(bDelet){
    		mhdfs.DeleteHDFSFile(Objfile);
    	}
    	
    	
    	//Hive Query
    	boolean funQuery = true;
    	if(funQuery){
    		String sql1 = "set hive.exec.mode.local.auto=true;";
    		mhivetool.funUpdate(conn, sql1);

    		// make query
    		String params2[] = {};
    		String Hivesql = "select * from " + tableName + " where day='"+partitionName +"'";
    		System.out.println(Hivesql);
    		List<Object[]> result = 
    				(List<Object[]>) mhivetool.query(conn, Hivesql, params2,new ArrayListHandler());
    		System.out.println("query done"); 
    		System.out.println("");

    		for(Object[] aa:result){
    			for (Object temp:aa){
    				System.out.print(temp +"   ");
    			}
    			System.out.println();
    		}
    	}
    	mhivetool.releaseConn(conn);
	}

}
