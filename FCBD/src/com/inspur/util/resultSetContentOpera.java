package com.inspur.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSessionBindingEvent;


/** 
 * @author LiuKai 
 * @version  
 */
public class resultSetContentOpera implements IFileContentOpera{
	private ResultSet res;
	private List<String[]> ilist = new ArrayList<String[]>();
	private List<String[]> outputList= new ArrayList<String[]>();
	private int MaxPageNum;
	private int CurrentPageNum;
	private int numEachPage;
	private boolean endFlag;
	
	/**
	 * 
	 * @param orgRes
	 * @throws SQLException 
	 */
	public resultSetContentOpera(ResultSet orgRes) throws SQLException{
		res =  orgRes;
		MaxPageNum = 0;
		CurrentPageNum = 0;
		numEachPage = 100;
		endFlag = false;  // flag of if res reach the end 
	}
	
	// 初始化
	public List<String[]> init() throws SQLException{
		
		outputList.clear();
		MaxPageNum = 0;
		CurrentPageNum =0;
		System.out.println("CurrentPageNum:"+CurrentPageNum);
		
		for(int i=0;i<numEachPage;i++){
			if(res.next()){
				String [] tempstr = new String[7];
				tempstr[0] = res.getString(1);
				tempstr[1] = res.getString(2);
				tempstr[2] = res.getString(3);
				tempstr[3] = res.getString(4);
				tempstr[4] = res.getString(5);
				tempstr[5] = res.getString(6);
				tempstr[6] = res.getString(7);
				outputList.add(tempstr);
				ilist.add(tempstr);
			}
		}
		if(outputList.size()<numEachPage){
			endFlag = true;
		}
		System.out.println(" 1 ilist size :"+ilist.size());

		
		for(int i=0;i<outputList.size();i++){
			System.out.print("  "+(outputList.get(i))[0]);
		}
		System.out.println();
		
		return outputList;	
	}
	
	
	public List<String[]> getPageContent(int pageIndex) throws SQLException{
		outputList.clear();
		
		if(pageIndex==1){
			if(CurrentPageNum==MaxPageNum){  //在最后一页
				for(int i=0;i<numEachPage;i++){
					if(res.next()){
						String [] tempstr = new String[7];
						tempstr[0] = res.getString(1);
						tempstr[1] = res.getString(2);
						tempstr[2] = res.getString(3);
						tempstr[3] = res.getString(4);
						tempstr[4] = res.getString(5);
						tempstr[5] = res.getString(6);
						tempstr[6] = res.getString(7);
						outputList.add(tempstr);
						ilist.add(tempstr);
					}
				}
				if(outputList.size()!=0){
					MaxPageNum++;
					CurrentPageNum++;
				}
				else{
					return null;
				}
				System.out.println(" 2  ilist size :"+ilist.size());
				System.out.println("CurrentPageNum:"+ CurrentPageNum);	
			}
			else{
				int startIndex = (CurrentPageNum+1)*numEachPage;
				System.out.println("CurrentPageNum:"+(CurrentPageNum+1));
				for(int i=0;i<numEachPage;i++){
					outputList.add(ilist.get(startIndex+i));
				}
				System.out.println(" 3  ilist size :"+ilist.size());
				CurrentPageNum++;
				System.out.println("CurrentPageNum:"+ CurrentPageNum);
			}
		}
		else if(pageIndex==-1){
			System.out.println("CurrentPageNum:"+(CurrentPageNum-1));
			int startIndex = (CurrentPageNum-1)*numEachPage;
			for(int i=0;i<numEachPage;i++){
				outputList.add(ilist.get(startIndex+i));
			}
			CurrentPageNum--;
			System.out.println(" 4 ilist size :"+ilist.size());
			
		}
		else{
			System.out.println("pageIndex is wrong!");
		}
		
		for(int i=0;i<outputList.size();i++){
			System.out.print("  "+outputList.get(i)[0]);
		}
		System.out.println();
		
		return outputList;
	}

	@Override
	public int getPageNum() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void valueBound(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
