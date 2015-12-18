package com.inspur.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;

import com.inspur.domain.DataFile;

public interface IDataFileDao {
	
	List<DataFile> getDataFlie(String owner_org_id) throws SQLException;
	
	String getDataFileID(String fileName) throws SQLException;
	
	List<Object[]> getFileContent(String Hivesql, Object[] params, ResultSetHandler<?> rsh) throws SQLException;
	
	
}
