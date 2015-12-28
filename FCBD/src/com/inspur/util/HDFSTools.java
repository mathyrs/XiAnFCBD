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
//import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
//import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
//import org.apache.hadoop.hive.common.FileUtils;
import org.apache.commons.io.*;

public class HDFSTools {  
	
	 /**
     * @method WriteToHDFS
     * @author liukai
     * @param Objfile:the path to objective file 
     * @param Sourfile: the path of source file
     * @throws IOException
     * @throws URISyntaxException
     */
    public void WriteToHDFS(String Objfile, String Sourfile) throws IOException, URISyntaxException
    {

        String Objfilefolder = Objfile.substring(0, Objfile.lastIndexOf("/")+1);
        boolean fexist = ListDirAll(Objfilefolder,Objfile);
		if(fexist){ // exist
			DeleteHDFSFile(Objfile); // clear Objfile in HDFS
		}
        
//        System.out.println("xxx"+Objfilefolder);
//    	System.out.println(Objfile);
//    	System.out.println(Sourfile);
    	List<String> lines = IOUtils.readLines(new FileInputStream(Sourfile), "UTF-8");  
    	Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(Objfile), conf);
        Path path = new Path(Objfile);
        FSDataOutputStream out = fs.create(path);   
        for(String a:lines){
//        	System.out.println(a);
            out.write((a + "\n").getBytes("UTF-8"));      
        }
        out.close();
        
    }
    
    
    public boolean ListDirAll(String DirFile,String fileName) throws IOException
    {     
    	boolean fexist = false;
    	Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(DirFile), conf);
        Path path = new Path(DirFile);
         
        FileStatus[] status = fs.listStatus(path);
        //����1  
        for(FileStatus f: status)
        {
        	fexist = fileName.equals(f.getPath().toString()); 
        }
        return fexist;
    }
    
    public void DeleteHDFSFile(String file) throws IOException
    {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(file), conf);
        Path path = new Path(file);
        //�鿴fs��delete API���Կ������������deleteonExitʵ���˳�JVMʱɾ������ķ�������ָ��ΪĿ¼�ǵݹ�ɾ��
        fs.delete(path,true);
        fs.close();
    }
    
    /**
     * @author LiuKai
     * @param file
     * @return
     * @throws IOException
     */
    public List<String> ReadFromHDFS(String file) throws IOException 
    {
    	/*
    	String tableName = "t" + file.substring(0, 5)+"2015010120150130";
		String partitionName = "day=p"+file.substring(6,14);
		String fileFolder = "hdfs://Master:9000//user/hive/warehouse/" + tableName +"/"+ partitionName;	
    	String filePath = fileFolder+"/"+file;
    	Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(filePath), conf);
        Path path = new Path(filePath);
        FSDataInputStream in = fs.open(path);
    	List<String> lines = IOUtils.readLines(in, "UTF-8");
        return lines;	
        */

    	String tableName = "t" + file.substring(0, 5)+"2015010120150130";
		String partitionName = "day=p"+file.substring(6,14);
		String fileFolder = "hdfs://Master:9000//user/hive/warehouse/" + tableName +"/"+ partitionName;	
    	String filePath = fileFolder+"/"+file;
    	Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(filePath), conf);
        Path path = new Path(filePath);
        FSDataInputStream in = fs.open(path);
    	List<String> lines = IOUtils.readLines(in, "UTF-8");
        return lines;
    }
    
    

    

	
	/*
    public void ReadFromHDFS(String file) throws IOException
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
   
    public void DeleteHDFSFile(String file) throws IOException
    {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(file), conf);
        Path path = new Path(file);
        //�鿴fs��delete API���Կ������������deleteonExitʵ���˳�JVMʱɾ������ķ�������ָ��ΪĿ¼�ǵݹ�ɾ��
        fs.delete(path,true);
        fs.close();
    }
     
    public void UploadLocalFileHDFS(String src, String dst) throws IOException
    {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(dst), conf);
        Path pathDst = new Path(dst);
        Path pathSrc = new Path(src);
         
        fs.copyFromLocalFile(pathSrc, pathDst);
        fs.close();
    }
     
    public void ListDirAll(String DirFile) throws IOException
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
