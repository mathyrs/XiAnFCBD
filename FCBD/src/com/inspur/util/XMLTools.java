package com.inspur.util;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XMLTools {
	private File XMLfile = null;
	private SAXReader reader = null;
	private Element root = null;
	///SAXReader用于解析XML文件
	public XMLTools(String filePath) {
		SAXReader reader = new SAXReader();
		File file = new File(filePath);
		Document document = null;
		
		try {
			document = reader.read(file);
			root = document.getRootElement();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	
			
}
