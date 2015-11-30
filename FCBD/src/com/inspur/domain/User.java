package com.inspur.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
/**
 * @author mathyrs
 * �û�ʵ����
 */
public class User implements Serializable {

    private static final long serialVersionUID = -4313782718477229465L;
    
    // �û�ID
    private int userID;
    // �û����ڵ�λID
    private String orgID;
    // �û���
    private String login_name;
    // ��¼����
    private String password;
    // �û�����
    private String user_name;
    // ����
    private String email;
    // ע��ʱ��
    private Timestamp gen_time;
    // ���ε�¼ʱ��
    private Timestamp login_time;
    // �ϴε�¼ʱ��
    private Timestamp last_login_time;
    // ��½����
    private int count;

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getOrgID() {
		return orgID;
	}

	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}

	public String getLogin_name() {
		return login_name;
	}

	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Timestamp getGen_time() {
		return gen_time;
	}

	public void setGen_time(Timestamp gen_time) {
		this.gen_time = gen_time;
	}

	public Timestamp getLogin_time() {
		return login_time;
	}

	public void setLogin_time(Timestamp login_time) {
		this.login_time = login_time;
	}

	public Timestamp getLast_login_time() {
		return last_login_time;
	}

	public void setLast_login_time(Timestamp last_login_time) {
		this.last_login_time = last_login_time;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

    
    
}

