package com.inspur.service.impl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.handlers.ArrayListHandler;

import com.inspur.dao.IDataFileDao;
import com.inspur.dao.impl.DataFileDaoImpl;
import com.inspur.domain.DataFile;
import com.inspur.domain.User;
import com.inspur.service.IDataFileService;
import com.inspur.util.ArrayListHandlerWithName;

public class DataFileServiceImpl implements IDataFileService {

	@Override
	public List<DataFile> getDataFile(String owner_org_id) throws SQLException {
		// TODO Auto-generated method stub
		DataFileDaoImpl dfi = new DataFileDaoImpl();
		
		return dfi.getDataFlie(owner_org_id);
	}

	@Override
	public String getDataFileID(String fileName) throws SQLException {
		// TODO Auto-generated method stub
		DataFileDaoImpl dfi = new DataFileDaoImpl();
		
		return dfi.getDataFileID(fileName);
	}

	@Override
	public List<String> getDataFileContent(String fileID) throws SQLException, IOException {
		// TODO Auto-generated method stub
		DataFileDaoImpl dfi = new DataFileDaoImpl();
//		String[] params = {};
//		String[] parameters = fileID.split("_");
//		String tableName = "t" + parameters[0] + "2015010120150130";
//		String partionName = "p" + parameters[1];
//		String flightName = parameters[2];
//		String Hivesql = "select * from " + tableName + 
//				   " where day='"+partionName +"'" +" AND flight ='"+flightName+"'";
		List<String> content = dfi.getFileContentByHDFS(fileID);
		
		return content;
	}

	@Override
	public void addDataFile(User user, String fileName, String sourcePath, String orgName,
			String fileTime, String permission, String sortie) throws SQLException, IOException, URISyntaxException {
		// TODO Auto-generated method stub
		DataFileDaoImpl dfi = new DataFileDaoImpl();
		//default tempt file path
		String filePath = "hdfs://Master:9000/user/hadoop/tmp/" + fileName;
		//generate file ID
		String fileID = fileName.substring(0,fileName.lastIndexOf(".txt"));
		String orgID = user.getOrg_id();
		
		dfi.addFiletoHDFS(filePath, sourcePath);
		dfi.addDataFile(filePath, "t" + orgID + "20152020", "p" + fileTime.replace("-", ""));
		dfi.deleteFiletoHDFS(filePath);
		dfi.addDataFileInfor(user, fileID, fileName, orgID, fileTime, sortie, permission);
	}

	@Override
	public List<Object[]> getHiveQueryResult(String orgID, String startTime, String endTime, String planeType, String sortie, String deviceName, String planeID) throws SQLException {
		// TODO Auto-generated method stub
		
		if (null == orgID || null == startTime || null == endTime) return null;
		
		if (null == deviceName || deviceName.equals("")) {
			deviceName = "!= 'XXX'";
		} else {
			deviceName = "= '" + deviceName + "'";
		}
		//表名后缀默认为20152020
		String hiveQL = "SELECT * FROM " + "t" + orgID + "20152020" + " WHERE day > '" + "p" + startTime + "' AND day < '" + "p" + endTime + "' AND name " + deviceName;
		System.out.println(hiveQL);
		IDataFileDao ifd = new DataFileDaoImpl();
		String[] params = {};
		return ifd.getFileContentByHive(hiveQL, params, new ArrayListHandlerWithName());
	}
	
	@Override
	public List<Object[]> getHiveQueryResult(String hiveQL) throws SQLException {
		// TODO Auto-generated method stub
		
		IDataFileDao ifd = new DataFileDaoImpl();
		String[] params = {};
		return ifd.getFileContentByHive(hiveQL, params, new ArrayListHandlerWithName());
	}
	
}
