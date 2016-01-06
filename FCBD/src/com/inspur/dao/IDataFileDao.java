package com.inspur.dao;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RserveException;

import com.inspur.domain.DataFile;
import com.inspur.domain.User;

public interface IDataFileDao {
	
	List<DataFile> getDataFlie(String owner_org_id) throws SQLException;
	
	String getDataFileID(String fileName) throws SQLException;
	
	List<String[]> getFileContentByHive(String Hivesql, String[] params, ResultSetHandler<?> rsh) throws SQLException;
	
	List<String> getFileContentByHDFS(String fileID) throws SQLException, IOException;
	
	List<String[]> getFileContentByRHive(String HDFSIP,String HiveQL,String RfileName) throws RserveException, REXPMismatchException;
	
	void addDataFile(String fileName, String tableName, String partitionName) throws SQLException;
	
	void addDataFileInfor(User user, String fileID, String fileName, String orgID, String fileTime, String sortie, String permission) throws SQLException;
	
	void addFiletoHDFS(String objFilePath, String sourcePath) throws IOException, URISyntaxException;
	
	void deleteFiletoHDFS(String objFilePath) throws IOException, URISyntaxException;
	
//	void removeDataFile() throws SQLException;
//	
//	void removeDataFileInfor() throws SQLException;
}
