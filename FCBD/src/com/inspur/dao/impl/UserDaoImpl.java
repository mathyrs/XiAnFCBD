package com.inspur.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.handlers.BeanHandler;

import com.inspur.dao.IUserDao;
import com.inspur.domain.User;
import com.inspur.util.DBOpera;
import com.inspur.util.DBTools;

public class UserDaoImpl implements IUserDao {

	@Override
	public User find(String userName, String userPwd) {
		// TODO Auto-generated method stub
		String[] params = new String[2];
		params[0] = userName;
		params[1] = userPwd;
		User user = null;
		try {
			user = (User) DBOpera
					.select("SELECT * FROM TUser WHERE login_name = ? AND password = ?;",
							new BeanHandler(User.class), params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBTools.close();
		}
		
		return user;
	}

	@Override
	public void add(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public User find(String userName) {
		// TODO Auto-generated method stub
		String[] params = new String[1];
		params[0] = userName;
		User user = null;
		try {
			user = (User) DBOpera.select(
					"SELECT * FROM TUser WHERE login_name = ?;", new BeanHandler(
							User.class), params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBTools.close();
		}
		return user;
	}

}
