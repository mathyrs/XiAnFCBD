package com.inspur.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
/**
 * @author mathyrs
 * 用户实体类
 */
public class User implements Serializable {

    private static final long serialVersionUID = -4313782718477229465L;
    
    // 用户ID
    private int user_id;
    // 用户所在单位ID
    private String org_id;
    // 用户名
    private String login_name;
    // 登录密码
    private String login_password;
    // 用户姓名
    private String user_name;
    // 邮箱
    private String email;
    // 注册时间
    private Timestamp gen_time;
    // 本次登录时间
    private Timestamp login_time;
    // 登陆次数
    private int login_count;
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getOrg_id() {
		return org_id;
	}
	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}
	public String getLogin_name() {
		return login_name;
	}
	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}
	public String getLogin_password() {
		return login_password;
	}
	public void setLogin_password(String login_password) {
		this.login_password = login_password;
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
	public int getLogin_count() {
		return login_count;
	}
	public void setLogin_count(int login_count) {
		this.login_count = login_count;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

    
    
}

