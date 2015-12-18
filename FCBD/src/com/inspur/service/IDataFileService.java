package com.inspur.service;

import java.sql.SQLException;
import java.util.List;

import com.inspur.domain.DataFile;

public interface IDataFileService {
	
	public List<DataFile> getDataFile(String owner_org_id) throws SQLException;
	
	public String getDataFileID(String fileName) throws SQLException;
	
	public String getDataFileContent(String fileID) throws SQLException;

}
