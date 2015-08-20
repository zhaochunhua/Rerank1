package com.util;

import org.dom4j.io.SAXReader;

public class ReadXml {

	/**read xml**/
	public static org.dom4j.Document readXml(String fileUrl){
		
		SAXReader reader = new SAXReader();
		org.dom4j.Document document = null;
		try{
			//System.out.println("开始读取");
			document = reader.read(fileUrl);
			//System.out.println("结束读取");
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return document;
	}
}
