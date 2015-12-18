package com.inspur.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.handlers.BeanHandler;

import com.inspur.dao.IUserDao;
import com.inspur.domain.User;
import com.inspur.util.DBOpera;
import com.inspur.util.DBTools;

public class UserDaoImpl implements IUserDao {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public User find(String userName, String userPwd) {
		// TODO Auto-generated method stub
		String[] params = new String[2];
		params[0] = userName;
		params[1] = userPwd;
		User user = null;
		try {
			user = (User) DBOpera
					.select("SELECT * FROM Tuser WHERE login_name = ? AND login_password = ?;",
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
	public void add(User user) throws SQLException {
		// TODO Auto-generated method stub
		String[] params = new String[6];
		params[0] = user.getOrg_id();
		params[1] = user.getLogin_name();
		params[2] = user.getLogin_password();
		params[3] = user.getUser_name();
		params[4] = user.getEmail();
		params[5] = user.getGen_time().toString();
		
		
		DBOpera.update("INSERT INTO Tuser (org_id, login_name, login_password, user_name, email, gen_time) VALUES ( ?, ?, ?, ?, ?, ?);", params);
		
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
