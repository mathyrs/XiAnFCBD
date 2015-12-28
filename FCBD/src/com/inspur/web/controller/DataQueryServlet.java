package com.inspur.web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.inspur.domain.User;
import com.inspur.service.IDataFileService;
import com.inspur.service.impl.DataFileServiceImpl;

public class DataQueryServlet extends HttpServlet {

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

		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		OutputStream ps = response.getOutputStream();
		
		//judge whether the page is timeout. 判断页面是否过期
		User user = (User)request.getSession().getAttribute("user");
		if (null == user) {
			ps.write("页面已过期，请重新登录！".getBytes("UTF-8"));
		}
		//get request type
		String requestType = request.getParameter("requestType");
		
		if (requestType.equals("query")) {
			
		} else if (requestType.equals("hive")) {
			String hiveQL = request.getParameter("HiveQL");
			IDataFileService ifs = new DataFileServiceImpl();
			try {
				List<Object[]> result = ifs.getHiveQueryResult(hiveQL);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				ps.write(e.getMessage().getBytes("UTF-8"));
				e.printStackTrace();
			}
		}
		
		
		ps.flush();
		ps.close();
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
