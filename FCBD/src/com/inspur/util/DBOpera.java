package com.inspur.util;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

public class DBOpera {
	
	public static void update(String sql, Object[] params) throws SQLException {
		QueryRunner qr = new QueryRunner();
		qr.update(DBTools.getConnection(), sql, params);
		
	}
	
	public static Object select(String sql, ResultSetHandler<?> bh, Object[] params) throws SQLException {
		QueryRunner qr = new QueryRunner();
		return qr.query(DBTools.getConnection(), sql, bh, params);
	}
	
	public static Object select(String sql, BeanListHandler<?> bh,
			Object[] params) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner qr = new QueryRunner();
		return qr.query(DBTools.getConnection(), sql, bh, params);
		
	}
	
	public static void batch(String sql, Object[][] params) throws SQLException {
		QueryRunner qr = new QueryRunner();
		qr.batch(DBTools.getConnection(), sql, params);
		
	}

	
}
