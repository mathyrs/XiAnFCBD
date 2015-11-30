package com.inspur.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;

/**
 * @ClassName: DBTools
 * @Description: ���ݿ����ӹ�����
 * @author: mathyrs
 * @date: 2015-11-25 13:50
 * 
 */
public class DBTools {
	/**
	 * ��java�У���д���ݿ����ӳ���ʵ��java.sql.DataSource�ӿڣ�ÿһ�����ݿ����ӳض���DataSource�ӿڵ�ʵ��
	 * DBCP���ӳؾ���java.sql.DataSource�ӿڵ�һ������ʵ��
	 */
	private static DataSource ds = null;

	// ʹ��ThreadLocal�洢��ǰ�߳��е�Connection����
	private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();
	// �ھ�̬������д������ݿ����ӳ�
	static {
		try {
			// ����dbcpconfig.properties�����ļ�
			InputStream in = DBTools.class.getClassLoader()
					.getResourceAsStream("dbcpconfig.properties");
			Properties prop = new Properties();
			prop.load(in);
			// ��������Դ
			ds = BasicDataSourceFactory.createDataSource(prop);
			in.close();
		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	/**
	 * @Method: getConnection
	 * @Description: ������Դ�л�ȡ���ݿ�����
	 * @Anthor:�°�����
	 * @return Connection
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		// �ӵ�ǰ�߳��л�ȡConnection
		Connection conn = threadLocal.get();
		if (conn == null) {
			// ������Դ�л�ȡ���ݿ�����
			conn = getDataSource().getConnection();
			// ��conn�󶨵���ǰ�߳�
			threadLocal.set(conn);
		}
		return conn;
	}

	/**
	 * @Method: release
	 * @Description: �ͷ���Դ��
	 *               �ͷŵ���Դ����Connection���ݿ����Ӷ��󣬸���ִ��SQL�����Statement���󣬴洢��ѯ�����ResultSet����
	 * @Anthor:�°�����
	 * 
	 * @param conn
	 * @param st
	 * @param rs
	 */
	public static void release(Connection conn, Statement st, ResultSet rs) {
		if (rs != null) {
			try {
				// �رմ洢��ѯ�����ResultSet����
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			rs = null;
		}
		if (st != null) {
			try {
				// �رո���ִ��SQL�����Statement����
				st.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		close();
	}

	/**
	 * @Method: startTransaction
	 * @Description: ��������
	 * @Anthor:�°�����
	 * 
	 */
	public static void startTransaction() {
		try {
			Connection conn = threadLocal.get();
			if (conn == null) {
				conn = getConnection();
				// �� conn�󶨵���ǰ�߳���
				threadLocal.set(conn);
			}
			// ��������
			conn.setAutoCommit(false);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Method: rollback
	 * @Description:�ع�����
	 * @Anthor:�°�����
	 * 
	 */
	public static void rollback() {
		try {
			// �ӵ�ǰ�߳��л�ȡConnection
			Connection conn = threadLocal.get();
			if (conn != null) {
				// �ع�����
				conn.rollback();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Method: commit
	 * @Description:�ύ����
	 * @Anthor:�°�����
	 * 
	 */
	public static void commit() {
		try {
			// �ӵ�ǰ�߳��л�ȡConnection
			Connection conn = threadLocal.get();
			if (conn != null) {
				// �ύ����
				conn.commit();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Method: close
	 * @Description:�ر����ݿ�����(ע�⣬��������Ĺرգ����ǰ����ӻ������ݿ����ӳ�)
	 * @Anthor:�°�����
	 * 
	 */
	public static void close() {
		try {
			// �ӵ�ǰ�߳��л�ȡConnection
			Connection conn = threadLocal.get();
			if (conn != null) {
				conn.close();
				// �����ǰ�߳��ϰ�conn
				threadLocal.remove();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Method: getDataSource
	 * @Description: ��ȡ����Դ
	 * @Anthor:�°�����
	 * @return DataSource
	 */
	public static DataSource getDataSource() {
		// ������Դ�л�ȡ���ݿ�����
		return ds;
	}

}
