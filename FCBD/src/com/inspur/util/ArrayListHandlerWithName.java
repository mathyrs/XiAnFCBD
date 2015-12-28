package com.inspur.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;


/**
 * @brief ArrayListHandlerWithName is supposed to implement interface ResultSetHandler<List<Object[]>> which change resultset object to a List<Object[]> with attribute names.
 * @author mathyrs
 * @date 2015/12/15
 */

public class ArrayListHandlerWithName implements ResultSetHandler<List<Object[]>>{

	@Override
	public List<Object[]> handle(ResultSet arg0) throws SQLException {
		// TODO Auto-generated method stub
		List<Object[]> result = new ArrayList<Object[]>();
		result.clear();
		ResultSetMetaData rsmd = arg0.getMetaData();
		Object[] paraNames = new Object[rsmd.getColumnCount()];
		for (int i = 0; i < paraNames.length; i++) {
			paraNames[i] = rsmd.getColumnName(i+1);
		}
		result.add(paraNames);
		while (arg0.next()) {
			Object[] values = new Object[paraNames.length];
			for (int i = 0; i < values.length; i++) {
				values[i] = arg0.getObject((String)paraNames[i]);
			}
			result.add(values);
		}
		
		
		return result;
	}

}
