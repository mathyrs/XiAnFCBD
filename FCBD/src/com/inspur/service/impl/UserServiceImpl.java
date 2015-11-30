package com.inspur.service.impl;

import com.inspur.dao.impl.UserDaoImpl;
import com.inspur.domain.User;
import com.inspur.exception.UserExistException;
import com.inspur.service.IUserService;

public class UserServiceImpl implements IUserService{

	@Override
	public void registerUser(User user) throws UserExistException {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public User loginUser(String userName, String userPwd) {
		// TODO Auto-generated method stub
		UserDaoImpl udi = new UserDaoImpl();
		return udi.find(userName, userPwd);
	}

}
