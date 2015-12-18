package com.inspur.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

import com.inspur.dao.IDataFileDao;
import com.inspur.domain.DataFile;
import com.inspur.domain.User;
import com.inspur.util.DBOpera;
import com.inspur.util.hiveTools;

public class DataFileDaoImpl implements IDataFileDao{

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

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getFileContent(String Hivesql, Object[] params,
			ResultSetHandler<?> rsh) throws SQLException {
		// TODO Auto-generated method stub
		hiveTools mhivetool = new hiveTools();
		
		return (List<Object[]>) mhivetool.query(mhivetool.getConnection(), Hivesql, (String[])params, rsh);
	}

}
