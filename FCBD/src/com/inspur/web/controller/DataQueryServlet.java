package com.inspur.web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.inspur.domain.User;
import com.inspur.service.IDataFileService;
import com.inspur.service.impl.DataFileServiceImpl;

/**
 * @brief DataQueryServlet makes response to data query request
 * @author mathyrs
 * @date 2015/12/28
 */

public class DataQueryServlet extends HttpServlet {

	/**
	 * @brief paint a table as response on request pages.
	 * @param String hiveSql, Object[] params, ResultSetHandler<?> rsh
	 * @return List<Object[]>
	 * @exception SQLException
	 * @version 0.1
	 * @author mathyrs
	 * @date 2015-12-15
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		OutputStream ps = response.getOutputStream();
//		System.out.println("get in!!!");
		//judge whether the page is timeout. 判断页面是否过期
		User user = (User)request.getSession().getAttribute("user");
		if (null == user) {
			ps.write("页面已过期，请重新登录！".getBytes("UTF-8"));
		}
		//get request type
		String requestType = request.getParameter("requestType");
		List<Object[]> result = null;
		if (requestType.equals("query")) {
			
			String orgID = request.getParameter("orgID");
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String planeType = request.getParameter("planeType");
			String sortie = request.getParameter("sortie");
			String deviceName = request.getParameter("deviceName");
			String planeID = request.getParameter("planeID");
			System.out.println(startTime);
			System.out.println(endTime);
			//格式化日期字符串
			SimpleDateFormat before = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat after = new SimpleDateFormat("yyyyMMdd");
			try {
				startTime = after.format(before.parse(startTime));
				endTime = after.format(before.parse(endTime));
				System.out.println(startTime);
				System.out.println(endTime);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			System.out.println(orgID);
			IDataFileService ifs = new DataFileServiceImpl();
			try {
				result = ifs.getHiveQueryResult(orgID, startTime, endTime, planeType, sortie, deviceName, planeID);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				ps.write(e.getMessage().getBytes("UTF-8"));
				e.printStackTrace();
			}
			
		} else if (requestType.equals("hive")) {
			String hiveQL = request.getParameter("HiveQL");
			IDataFileService ifs = new DataFileServiceImpl();
			try {
				result = ifs.getHiveQueryResult(hiveQL);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				ps.write(e.getMessage().getBytes("UTF-8"));
				e.printStackTrace();
			}
		}
		if (null != result) {
			ps.write("<table border=\"1\" id=\"resultTable\" style=\"position:relative;background-color:white;\">".getBytes("UTF-8"));
			for (Object[] temp : result) {
				ps.write("<tr>".getBytes("UTF-8"));
				for (Object tmp : temp) {
					ps.write(("<td width=140px>" + tmp + "</td>").getBytes("UTF-8"));
				}
				ps.write("</tr>".getBytes("UTF-8"));
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

//		System.out.println("get in!!!");
		doGet(request, response);
	}

}
