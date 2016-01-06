package com.inspur.util;

import java.util.ArrayList;
import java.util.List;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.RList;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

/** 
 * @author LiuKai 
 * @version  
 */
public class RHiveDemo {
	public static void main(String[] args ) throws RserveException, REXPMismatchException{
		RHiveTools irhivetool = new RHiveTools();
		String HiveQL = " select * from t1112120152020 where day = 'p20150101' ";
		String RfileName = "FC2.R";
		List<String[]> arr = irhivetool.RhiveQuery("192.168.1.3", HiveQL,RfileName);
		
		
		String HDFSFileFolder = "hdfs://Master:9000/user/hadoop/tmp/";
		String localFileFloder = "D:\\simulatedData1\\";
		String HDFSFileName = "aa.pdf";
		String HDFSFilePath = HDFSFileFolder + HDFSFileName;
		String localFilePath = localFileFloder + HDFSFileName;
		
		HDFSTools.copyFileHDFS2Local(localFilePath, HDFSFilePath);
		
	}
	
//	/**
//	 * 
//	* @Title: REXP2String 
//	* @Description: change REXP to String
//	* @param @param mRexp
//	* @param @param b
//	* @param @throws REXPMismatchException  
//	* @return void 
//	* @throws
//	 */
//	public void REXP2String(REXP mRexp,List<String[]> b) throws REXPMismatchException{
//		REXP y;
//
//		System.out.println("the size of REXP is "+ mRexp.length());
//		RList list = new RList();
//		if(mRexp.isList()){
//			list = mRexp.asList();
//			System.out.println("the size of list is "+list.size());
//		}
//		else{
//			System.out.println("mRexp is not a list");
//		}
//		for(int ilist = 0;ilist<list.size();ilist++){
//			y = list.at(ilist);
//			String namearray[] = y.asStrings();
//			b.add(namearray);
//		}
//	}
	
//	public void callRserve() throws RserveException, REXPMismatchException{
//		RConnection rconn = new RConnection("192.168.1.3");
//        REXP x = null;
//        rconn.eval("setwd(\"/home/hadoop/Program_R\")");
//        rconn.eval("source('FC2.R')");
//        System.out.println("1");
//        
//        String HiveQL = " select * from t1112120152020 where day = 'p20150101' ";
//        rconn.assign("HiveQL", HiveQL);
//        REXP rexp = rconn.eval("sd2(HiveQL)");
//        System.out.println("2");
//        
//        // transfer REXP to String
//        List<String[]> arr = new ArrayList<String[]>();
//        REXP2String(rexp,arr);
//        // display REXP
//        
//        for(int i=0;i<arr.size();i++){
//            String [] tmp1 = arr.get(i);
//            for(int j = 0;j<1;j++){
//            	System.out.println(tmp1[j] + "   ");    		
//            }
//        }
//	}
	


}
