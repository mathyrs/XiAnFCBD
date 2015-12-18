/*==========================================================================
 File name = FileShowServlet.java
 Version: 0.1
 Time of release: 2015-12-15
All rights reserved by Inspur Group forever, any person or organization must get written permission from Inspur Group to copy a part or whole of this code. Otherwise, Inspur Group will pursue any loss from the copy, including any financial and patent loss, etc.
Copyright is protected from 2015-
----------------------------------------------------------------------------
 Modified records:
 Date            reviser 
 YYYY/MM/DD      XXXX          
 ----------------------------------------------------------------------------
 Indentation:
Tab = 4
==========================================================================*/

/**
 *  @file FileShowServlet.java
 *  @author mathyrs
 *  @version 0.1
 *  @date 2015/12/15
 *  @reviser 
 */


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
import com.inspur.domain.DataFile;
import com.inspur.service.impl.DataFileServiceImpl;

/**
 * @brief FileShowServlet is supposed to return file list or file content to page main.html by reading database and HIVE.
 * @author mathyrs
 * @date 2015/12/15
 */

public class FileShowServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @brief implements main function of this class, requests value of orgID is like "11121" or "11121_20150101_FJ-10B.txt". if orgID records file name, it must end with ".txt"
	 * @param 
	 * @return 
	 * @exception
	 * @version 0.1
	 * @author mathyrs
	 * @date 2015-12-15
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		DataFileServiceImpl dfi = new DataFileServiceImpl();
		response.setHeader("Content-type", "text/html;charset=UTF-8"); 
		response.setCharacterEncoding("UTF-8");
		OutputStream ps = response.getOutputStream();
//		ServletContext context = getServletContext();
		//orgID receive organization ID generally, but if end with ".txt" it will be handled as data file name;
		String orgID = request.getParameter("orgID");
		System.out.println(orgID);
		if (null == orgID) {
			ps.write("orgID == null <br/>".getBytes("UTF-8"));
			System.out.println("orgID == null <br/>");
		} else {
			if (orgID.endsWith(".txt")) {
				try {
					String fileID = dfi.getDataFileID(orgID);
					String fileContent = dfi.getDataFileContent(fileID);
					ps.write(fileContent.getBytes("UTF-8"));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				try {
					System.out.println(orgID);
					List<DataFile> fileList = dfi.getDataFile(orgID);
					for (DataFile temp:fileList) {
						ps.write(temp.getFile_name().getBytes("UTF-8"));
						ps.write("<br/>".getBytes("UTF-8"));
					}
					
//					context.removeAttribute("fileList");
//					context.setAttribute("fileList", fileList);
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					ps.write("file no found!!<br/>".getBytes("UTF-8"));
					System.out.println("file no found!!<br/>");
				}
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

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

}
