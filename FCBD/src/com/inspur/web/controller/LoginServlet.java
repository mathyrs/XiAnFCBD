package com.inspur.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.inspur.domain.User;
import com.inspur.service.impl.UserServiceImpl;

public class LoginServlet extends HttpServlet {

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

		User user = null;
		UserServiceImpl usi = new UserServiceImpl();
		String userName = request.getParameter("user");
		String userPwd = request.getParameter("password");
		/*
		Class req = request.getClass();
		Method[] methods= req.getMethods();
		System.out.println(req.getName());
		for (int i = 0; i < methods.length; i++) {
			System.out.println(methods[i].toString());
		}
		*/
		//for test and study
		Cookie[] cookies = request.getCookies();
		if (null == cookies) {
			System.out.println("Can not find cookies!!!");
		} else {
			for (Cookie cook : cookies) {
				System.out.println(cook.getName() + ": " + cook.getValue());
			}
		}
		
		
		
		user = usi.loginUser(userName, userPwd);
		if (null != user) {
			request.getSession().setAttribute("user", user);
			request.getRequestDispatcher(response.encodeRedirectURL("/main.jsp")).forward(request, response);
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
