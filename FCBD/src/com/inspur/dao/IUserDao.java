package com.inspur.dao;

import com.inspur.domain.User;

public interface IUserDao {

	 /**
     * �����û����������������û�
     * @param userName
     * @param userPwd
     * @return �鵽�����û�
     */
    User find(String userName, String userPwd);

    /**
     * ����û�
     * @param user
     */
    void add(User user) throws Exception;

    /**�����û����������û�
     * @param userName
     * @return �鵽�����û�
     */
    User find(String userName);
}
