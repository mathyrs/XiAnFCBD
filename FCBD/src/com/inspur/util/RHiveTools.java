package com.inspur.util;

import java.util.ArrayList;
import java.util.Arrays;
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
public class RHiveTools {
	/**
	 * 
	* @Title: REXP2String 
	* @Description: change REXP to String
	* @param @param mRexp
	* @param @param b
	* @param @throws REXPMismatchException  
	* @return void 
	* @throws
	 */
	public void REXP2String(REXP mRexp,List<String[]> b) throws REXPMismatchException{
		REXP y;

		System.out.println("the size of REXP is "+ mRexp.length());
		RList list = new RList();
		if(mRexp.isList()){
			list = mRexp.asList();
			System.out.println("the size of list is "+list.size());
			for(int ilist = 0;ilist<list.size();ilist++){
				y = list.at(ilist);
				String namearray[] = y.asStrings();
				b.add(namearray);
			}
		}
		else{
			System.out.println("mRexp is not a list");
			String recordArray[] = mRexp.asStrings();
			System.out.println("the length of recordArray[] is "+ recordArray.length);
			int index = 0;
			int lenRecord = 8;
			if(recordArray.length%lenRecord==0){
				int numRecord = recordArray.length/lenRecord;
				for(int i = 0;i<numRecord;i++){
					String subRecordArray[] = 
							Arrays.copyOfRange(recordArray, i*lenRecord+0, i*lenRecord+7);
					b.add(subRecordArray);
				}				
			}			
		}

	}
	
	public List<String[]> RhiveQuery(String HDFSIP,String HiveQL,String RfileName) throws RserveException, REXPMismatchException{
		RConnection rconn = new RConnection(HDFSIP);
		
        rconn.eval("setwd(\"/home/hadoop/Program_R\")");
        String temp = "source(\'"+RfileName+"\')";
        rconn.eval(temp);
        rconn.assign("HiveQL", HiveQL);
        REXP rexp = rconn.eval("sd2(HiveQL)");
        
       // transfer REXP to String
//        List<String[]> arr = new ArrayList<String[]>();
//        REXP2String(rexp, arr);
        
        // display REXP
//        System.out.println("the num of record is " + arr.size());
//        for(int i=0;i<arr.size();i++){
//            String [] tmp1 = arr.get(i);
//            for(int j = 0;j<tmp1.length;j++){
//            	System.out.print(tmp1[j] + "   ");    		
//            }
//            System.out.println();
//        }
        return null;
		
	}

}
