package com.inspur.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.inspur.domain.User;
import com.inspur.exception.UserExistException;
import com.inspur.service.impl.UserServiceImpl;

public class RegisterServlet extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User user = new User();
		UserServiceImpl usi = new UserServiceImpl();
		/*
		String orgID = request.getParameter("orgID");
		String userName = request.getParameter("user");
		String userPwd = request.getParameter("password");
		String personName = request.getParameter("userName");
		String email = request.getParameter("email");*/
		
		//code for test
		String orgID = (String) request.getAttribute("orgID");
		String userName = (String) request.getAttribute("user");
		String userPwd = (String) request.getAttribute("password");
		String personName = (String) request.getAttribute("userName");
		String email = (String) request.getAttribute("email");
		
		user.setOrg_id(orgID);
		user.setLogin_name(userName);
		user.setLogin_password(userPwd);
		user.setUser_name(personName);
		user.setEmail(email);
		user.setGen_time(new Timestamp(new java.util.Date().getTime()));
		try {
			usi.registerUser(user);
		} catch (UserExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
