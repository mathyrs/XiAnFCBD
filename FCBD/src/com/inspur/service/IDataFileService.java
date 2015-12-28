package com.inspur.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;

import com.inspur.domain.DataFile;
import com.inspur.domain.User;

public interface IDataFileService {
	
	public List<DataFile> getDataFile(String owner_org_id) throws SQLException;
	
	public String getDataFileID(String fileName) throws SQLException;
	
	public List<String> getDataFileContent(String fileID) throws SQLException, IOException;
	
	public void addDataFile(User user, String fileName, String sourcePath, String orgName,
			String fileTime, String permission, String sortie) throws SQLException, IOException, URISyntaxException;
	
	public List<Object[]> getHiveQueryResult(String hiveQL) throws SQLException;


}
