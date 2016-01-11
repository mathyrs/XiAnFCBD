/*==========================================================================
 File name = IFileContentOpera.java
 Version: 0.1
 Time of release: 2016-01-06
All rights reserved by Inspur Group forever, any person or organization must get written permission from Inspur Group to copy a part or whole of this code. Otherwise, Inspur Group will pursue any loss from the copy, including any financial and patent loss, etc.
Copyright is protected from 2016-
----------------------------------------------------------------------------
 Modified records:
 Date            reviser 
 YYYY/MM/DD      XXXX          
 ----------------------------------------------------------------------------
 Indentation:
Tab = 4
==========================================================================*/

/**
*  @file IFileContentOpera.java
*  @author 杨若松
*  @version 0.1
*  @date 2016/1/6
*/

package com.inspur.util;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSessionBindingListener;

/**
 * @brief IFileContentOpera gives interfaces for file content shown in web pages.
 * @author mathyrs
 * @date 2016/1/6
 */

public interface IFileContentOpera extends HttpSessionBindingListener {
	
	
	/**
	 * @brief return the total number of file content pages. 返回总页数。
	 * @param 
	 * @return int
	 * @exception 
	 * @version 0.1
	 * @author mathyrs
	 * @throws SQLException 
	 * @date 2016/1/6
	 */
	int getPageNum() throws SQLException;
	
	
	/**
	 * @brief return the file content String of given page number as html table style. 以html表格语句形式返回给定页码页面需要显示的文件内容。
	 * @param int index
	 * @return String
	 * @exception 
	 * @version 0.1
	 * @author mathyrs
	 * @throws SQLException 
	 * @date 2016/1/6
	 */
	//List<String[]> getPageContent(int pageIndex) throws SQLException;
	
	public List<String[]> getPageContent(int pageIndex) throws SQLException;
	
	public List<String[]> init() throws SQLException;

}
