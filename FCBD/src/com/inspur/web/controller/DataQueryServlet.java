package com.inspur.web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RserveException;

import com.inspur.domain.User;
import com.inspur.service.IDataFileService;
import com.inspur.service.impl.DataFileServiceImpl;
import com.inspur.util.HDFSTools;
import com.inspur.util.IFileContentOpera;
import com.inspur.util.resultSetContentOpera;

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
			ps.flush();
			ps.close();
			return;
		}
		//get request type
		String requestType = request.getParameter("requestType");
		//final result supposed to be display on web page.
		List<String[]> result = null;
		IFileContentOpera rsco = null;
		
		String method = request.getParameter("method");
		IDataFileService ifs = new DataFileServiceImpl();
		//if value of method is "paint", the request is paint images by R.
		if (method != null && method.equals("paint")) {
			try {
				long start = System.currentTimeMillis();
				ifs.getRHiveQueryResult("select * from tmp");
				long end1 = System.currentTimeMillis();
				String imageFileName = UUID.randomUUID().toString();
				HDFSTools.copyFileHDFS2Local(this.getServletContext().getRealPath("/resources/tmpImages/" + imageFileName + ".pdf"), "hdfs://Master:9000/user/hadoop/tmp/" + "aa.pdf");
				request.getSession().setAttribute("imageFileName", imageFileName);
				ps.write(imageFileName.getBytes("UTF-8"));
				long end2 = System.currentTimeMillis();
				System.out.println("RqueryTime:" + (end1-start));
				System.out.println("copyTime:" + (end2-end1));
				
			} catch (RserveException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (REXPMismatchException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//if value of requestType is "query", the request is data query by given parameters.
		} else if (requestType.equals("query")) {
			
			String orgID = request.getParameter("orgID");
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String planeType = request.getParameter("planeType");
			String sortie = request.getParameter("sortie");
			String deviceName = request.getParameter("deviceName");
			String planeID = request.getParameter("planeID");

			//格式化日期字符串
			SimpleDateFormat before = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat after = new SimpleDateFormat("yyyyMMdd");
			try {
				startTime = after.format(before.parse(startTime));
				endTime = after.format(before.parse(endTime));

			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
//			System.out.println(orgID);
			
			//if value of method is "HiveQL", the request is hiveQL sentence generate by given parameters.
			if (method != null && method.equals("HiveQL")) {
				String HiveQL = ifs.getHiveQL(orgID, startTime, endTime, planeType, sortie, deviceName, planeID);
				System.out.println("ajax:" + HiveQL);
				ps.write(HiveQL.getBytes("UTF-8"));
				
			} else {
				try {
					rsco = ifs.HiveQueryForResultSet(orgID, startTime, endTime, planeType, sortie, deviceName, planeID);
//					result = ifs.getHiveQueryResult(orgID, startTime, endTime, planeType, sortie, deviceName, planeID);
					//write result object into session;
					request.getSession().setAttribute("result", rsco);
					result = rsco.init();

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					ps.write(e.getMessage().getBytes("UTF-8"));
					e.printStackTrace();
				} 
			}
			
			//if value of requestType is "hive", the request is data query by given hiveQL.
		} else if (requestType.equals("hive")) {
			String hiveQL = request.getParameter("HiveQL");
			try {
				rsco = ifs.HiveQueryForResultSet(hiveQL);
//				result = ifs.getHiveQueryResult(hiveQL);
				//write result object into session;
//				request.getSession().setAttribute("result", result);
				request.getSession().setAttribute("result", rsco);
				result = rsco.init();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				ps.write(e.getMessage().getBytes("UTF-8"));
				e.printStackTrace();
			} 
		} else if (null != method && method.equals("next")) {
			IFileContentOpera content = (IFileContentOpera) request.getSession().getAttribute("result");
			if (null != content) {
				try {
					result = content.getPageContent(1);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
		} else if (null != method && method.equals("previous")) {
			IFileContentOpera content = (IFileContentOpera) request.getSession().getAttribute("result");
			if (null != content) {
				try {
					result = content.getPageContent(-1);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		//get the result
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
