package com.inspur.util;

import java.util.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileUtil;
//import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
//import org.apache.hadoop.hive.common.FileUtils;
import org.apache.commons.io.*;

public class HDFSTools {  
	
	/**
	 * 
	* @Title: WriteToHDFS 
	* @Description: write file to HDFS 
	* @param @param HDFSfilePath
	* @param @param Sourfile
	* @param @throws IOException
	* @param @throws URISyntaxException  
	* @return void 
	* @throws
	 */
    public static void WriteToHDFS(String HDFSfilePath, String SourfilePath) throws IOException, URISyntaxException
    {

    	// delete the HDFS file if it has exist
        boolean isHDFSfileExist = isHDFSFileExist(HDFSfilePath);
		if(isHDFSfileExist){
			DeleteHDFSFile(HDFSfilePath);
		}
        
    	List<String> lines = IOUtils.readLines(new FileInputStream(SourfilePath), "UTF-8");
    	
    	Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(HDFSfilePath), conf);
        Path path = new Path(HDFSfilePath);
        FSDataOutputStream out = fs.create(path);   
        for(String a:lines){
            out.write((a + "\n").getBytes("UTF-8"));      
        }
        out.close();
        fs.close();    
    }
    
    /**
     * 
    * @Title: copyFileLocalHDFS 
    * @Description: copy a file from local to HDFS 
    * @param @param localFile
    * @param @param hadoopFile
    * @param @return  
    * @return boolean 
    * @throws
     */
    public static  boolean copyFileLocal2HDFS(String localFile,String hadoopFile){
		try {
			Configuration conf=new Configuration();
			FileSystem src=FileSystem.getLocal(conf);
			FileSystem dst= FileSystem.get(conf);
			Path srcpath = new Path(localFile);
			Path dstpath = new Path(hadoopFile);
			FileUtil.copy(src, srcpath, dst, dstpath,false,conf);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}  

		return true;
	}
    
	/**
	 * 
	* @Title: copyFileHDFS2Local 
	* @Description: copy a file from HDFS to local
	* @param @param localFile
	* @param @param hadoopFile
	* @param @return  
	* @return boolean 
	* @throws
	 */
	public static boolean copyFileHDFS2Local(String localFile,String hadoopFile){
		Configuration conf=new Configuration();
		
//		conf.addResource(new Path(HDFSTools.class.getClass().getResource("core-site.xml").getPath()));
//	    conf.addResource(new Path(HDFSTools.class.getClass().getResource("hdfs-site.xml").getPath()));
//	    System.out.println(HDFSTools.class.getClass().getResource("core-site.xml").getPath());
		try {
			FileSystem dst=FileSystem.getLocal(conf);
			FileSystem src= FileSystem.get(conf);
			Path dstpath = new Path(localFile);
			Path srcpath = new Path(hadoopFile);
			// delete the file after copy
			FileUtil.copy(src, srcpath, dst, dstpath,true,conf);
			
		} catch (IOException e) {
			// Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
    
    
    /**
     * 
    * @Title: isHDFSFileExist 
    * @Description: check if HDFS file exist 
    * @param @param HDFSfilePath
    * @param @return
    * @param @throws IOException  
    * @return boolean 
    * @throws
     */
    public static boolean isHDFSFileExist(String HDFSfilePath) throws IOException{
    	Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(HDFSfilePath), conf);
        Path path = new Path(HDFSfilePath);
        boolean isExist = fs.exists(path);
        fs.close();
        return isExist;
    }
    
    /**
     * 
    * @Title: DeleteHDFSFile 
    * @Description: delete HDFS File 
    * @param @param file
    * @param @throws IOException  
    * @return void 
    * @throws
     */
    public static void DeleteHDFSFile(String HDFSfilePath) throws IOException
    {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(HDFSfilePath), conf);
        Path path = new Path(HDFSfilePath);
        fs.delete(path,true);
        fs.close();
    }
    
    
    public static boolean isFileExist(String DirFile,String fileName) throws IOException
    {     
    	boolean fexist = false;
    	Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(DirFile), conf);
        Path path = new Path(DirFile);
         
        FileStatus[] status = fs.listStatus(path); 
        for(FileStatus f: status)
        {
        	fexist = fileName.equals(f.getPath().toString()); 
        }
        return fexist;
    }
    
    /**
     * 
    * @Title: clearHDFSFileFolder 
    * @Description: clear all the HDFS file in folder 
    * @param @param HDFSFileFolder
    * @param @throws IOException  
    * @return void 
    * @throws
     */
    public static void clearHDFSFileFolder(String HDFSFileFolder) throws IOException{
    	Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(HDFSFileFolder), conf);
        Path path = new Path(HDFSFileFolder);
         
        FileStatus[] status = fs.listStatus(path);
        for(FileStatus f: status){
        	String tmp= f.getPath().toString();
        	String tmp2 = tmp;
        	System.out.println("**"+tmp2);
        	DeleteHDFSFile(tmp2);      	
        }
        fs.close();
        
    }
    

    
    /**
     * @author LiuKai
     * @param file
     * @return
     * @throws IOException
     */
    public static List<String> ReadFromHDFS(String file) throws IOException 
    {
    	String tableName = "t" + file.substring(0, 5)+"20152020";
		String partitionName = "day=p"+file.substring(6,14);
		String fileFolder = "hdfs://Master:9000//user/hive/warehouse/" + tableName +"/"+ partitionName;	
    	String filePath = fileFolder+"/"+file;
    	Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(filePath), conf);
        Path path = new Path(filePath);
        FSDataInputStream in = fs.open(path);
        long startTime = System.currentTimeMillis();
    	List<String> lines = IOUtils.readLines(in, "UTF-8");
    	long endTime = System.currentTimeMillis();
    	System.out.println("ReadFromHDFS:" + (endTime - startTime));
    	in.close();
        return lines;
    }
    
    

    

	
	/*
    public static void ReadFromHDFS(String file) throws IOException
    {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(file), conf);
        Path path = new Path(file);
        FSDataInputStream in = fs.open(path);
        //LineIterator it = IOUtils.lineIterator(in, "UTF-8");  
        LineIterator it = IOUtils.lineIterator(in, "UTF-8");  
        System.out.println(it.hasNext());  
        if (it.hasNext()) {  
          String line = it.nextLine();  
          System.out.println(line);  
        }  
        List<String> lines = IOUtils.readLines(in, "UTF-8");
        for(String a:lines){
        	
        	System.out.println(a);
        }
    }
   
    public static void DeleteHDFSFile(String file) throws IOException
    {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(file), conf);
        Path path = new Path(file);
        //�鿴fs��delete API���Կ������������deleteonExitʵ���˳�JVMʱɾ������ķ�������ָ��ΪĿ¼�ǵݹ�ɾ��
        fs.delete(path,true);
        fs.close();
    }
     
    public static void UploadLocalFileHDFS(String src, String dst) throws IOException
    {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(dst), conf);
        Path pathDst = new Path(dst);
        Path pathSrc = new Path(src);
         
        fs.copyFromLocalFile(pathSrc, pathDst);
        fs.close();
    }
     
    public static void ListDirAll(String DirFile) throws IOException
    {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(DirFile), conf);
        Path path = new Path(DirFile);
         
        FileStatus[] status = fs.listStatus(path);
        //����1  
        for(FileStatus f: status)
        {
            System.out.println(f.getPath().toString());  
        }
        //����2  
        Path[] listedPaths = FileUtil.stat2Paths(status);  
        for (Path p : listedPaths){ 
          System.out.println(p.toString());
        }
    }
    */
    
   

}
