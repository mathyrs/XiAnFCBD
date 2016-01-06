package com.inspur.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;

import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RserveException;

import com.inspur.domain.DataFile;
import com.inspur.domain.User;

public interface IDataFileService {
	
	public List<DataFile> getDataFile(String owner_org_id) throws SQLException;
	
	public String getDataFileID(String fileName) throws SQLException;
	
	public List<String> getDataFileContent(String fileID) throws SQLException, IOException;
	
	public void addDataFile(User user, String fileName, String sourcePath, String orgName,
			String fileTime, String permission, String sortie) throws SQLException, IOException, URISyntaxException;
	
	public String getHiveQL(String orgID, String startTime, String endTime, String planeType, String sortie, String deviceName, String planeID);
	
	public List<String[]> getHiveQueryResult(String orgID, String startTime, String endTime, String planeType, String sortie, String deviceName, String planeID) throws SQLException;
	
	public List<String[]> getHiveQueryResult(String hiveQL) throws SQLException;
	
	public List<String[]> getRHiveQueryResult(String orgID, String startTime, String endTime, String planeType, String sortie, String deviceName, String planeID) throws SQLException, RserveException, REXPMismatchException;
	
	public List<String[]> getRHiveQueryResult(String hiveQL) throws SQLException, RserveException, REXPMismatchException;


}
