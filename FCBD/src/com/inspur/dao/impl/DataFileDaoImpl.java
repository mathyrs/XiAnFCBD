package com.inspur.dao.impl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

import com.inspur.dao.IDataFileDao;
import com.inspur.domain.DataFile;
import com.inspur.domain.User;
import com.inspur.util.DBOpera;
import com.inspur.util.HDFSTools;
import com.inspur.util.hiveTools;

/**
 * @brief DataFileDaoImpl is supposed to implement interface IDataFileDao which contains all operation of data file.
 * @author mathyrs
 * @date 2015/12/15
 */
public class DataFileDaoImpl implements IDataFileDao{
	//get data file by its org
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<DataFile> getDataFlie(String owner_org_id) throws SQLException {
		// TODO Auto-generated method stub
		List<DataFile> fileList = null;
		String[] params = new String[1];
		params[0] = owner_org_id;
		fileList = (List<DataFile>)DBOpera.
				select("SELECT * FROM TFile WHERE owner_org_id = ?;",
				new BeanListHandler(DataFile.class), params);
		return fileList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getDataFileID(String fileName) throws SQLException {
		// TODO Auto-generated method stub
		String fileID = null;
		List<String> list = null;
		String[] params = new String[1];
		params[0] = fileName;
		list = (List<String>) DBOpera.
				select("SELECT file_id FROM TFile WHERE file_name = ?;",
				new ColumnListHandler<String>(), params);
		fileID = list.get(0);
		return fileID;
	}
	
	
	
	/**
	 * @brief get file content from hive by hiveSQL. Each data saved as Object[].
	 * @param String hiveSql, Object[] params, ResultSetHandler<?> rsh
	 * @return List<Object[]>
	 * @exception SQLException
	 * @version 0.1
	 * @author mathyrs
	 * @date 2015-12-15
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getFileContentByHive(String hiveSql, String[] params,
			ResultSetHandler<?> rsh) throws SQLException {
		// TODO Auto-generated method stub
		hiveTools mhivetool = new hiveTools();
		return (List<Object[]>) mhivetool.query(mhivetool.getConnection(), hiveSql, (String[])params, rsh);
	}
	
	

	@Override
	public void addDataFile(String fileName, String tableName, String partitionName)
			throws SQLException {
		// TODO Auto-generated method stub
		hiveTools mhivetool = new hiveTools();
		String tableFormat = 
				   " (ID int, TIME string, a int, b int, name string, value double) ";
		mhivetool.HDFSuploadHive(mhivetool.getConnection(),fileName,tableName,partitionName,tableFormat);
	}

	@Override
	public void addDataFileInfor(User user, String fileID, String fileName, String orgID, String fileTime, String sortie, String permission) throws SQLException {
		// TODO Auto-generated method stub
		Object[] params = new String[7];
		params[0] = fileID;
		params[1] = fileName;
		params[2] = String.valueOf(user.getUser_id());
		params[3] = orgID;
		params[4] = sortie;
		params[5] = fileTime;
		params[6] = permission;
		DBOpera.update("insert into TFile Values(?,?,?,?,?,?,?)", params);
	}
	
	/**
	 * @brief get file content from HDFS by the unique fileID. Each row saved as a String. 
	 * @param String fileID
	 * @return List<String>
	 * @exception SQLException, IOException
	 * @version 0.1
	 * @author mathyrs
	 * @date 2015-12-15
	 */
	@Override
	public List<String> getFileContentByHDFS(String fileID) throws SQLException, IOException {
		// TODO Auto-generated method stub
    	HDFSTools mhdfs = new HDFSTools();
        List<String> lines = mhdfs.ReadFromHDFS(fileID + ".txt");
		return lines;
	}

	@Override
	public void addFiletoHDFS(String objFilePath, String sourcePath) throws IOException, URISyntaxException{
		// TODO Auto-generated method stub
		HDFSTools ht = new HDFSTools();
		ht.WriteToHDFS(objFilePath, sourcePath);
	}

	@Override
	public void deleteFiletoHDFS(String objFilePath) throws IOException,
			URISyntaxException {
		// TODO Auto-generated method stub
		HDFSTools ht = new HDFSTools();
		ht.DeleteHDFSFile(objFilePath);
	}

}
