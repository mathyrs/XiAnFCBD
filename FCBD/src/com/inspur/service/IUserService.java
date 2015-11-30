package com.inspur.service;

import com.inspur.domain.User;
import com.inspur.exception.UserExistException;


public interface IUserService {

    /**
     * �ṩע�����
     * @param user
     * @throws UserExistException
     */
    void registerUser(User user) throws UserExistException;

    /**
     * �ṩ��¼����
     * @param userName
     * @param userPwd
     * @return
     */
    User loginUser(String userName, String userPwd);
}
