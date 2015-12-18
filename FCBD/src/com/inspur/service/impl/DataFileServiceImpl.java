package com.inspur.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.handlers.ArrayListHandler;

import com.inspur.dao.impl.DataFileDaoImpl;
import com.inspur.domain.DataFile;
import com.inspur.service.IDataFileService;

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
	public String getDataFileContent(String fileID) throws SQLException {
		// TODO Auto-generated method stub
		DataFileDaoImpl dfi = new DataFileDaoImpl();
		String[] params = {};
		String[] parameters = fileID.split("_");
		String tableName = "t" + parameters[0] + "2015010120150130";
		String partionName = "p" + parameters[1];
		String flightName = parameters[2];
		String Hivesql = "select * from " + tableName + 
				   " where day='"+partionName +"'" +" AND flight ='"+flightName+"'";
		List<Object[]> content = dfi.getFileContent(Hivesql, params, new ArrayListHandler());
		String result = "";
		for(Object[] text:content){
			for (Object temp:text)
				result += temp;
			result += "<br/>";
		}
		return result;
	}
	
	
	
}
