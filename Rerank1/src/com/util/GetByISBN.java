package com.util;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.Element;

public class GetByISBN implements Variable_2{

	public static String getByISBN(String isbn){
		String lastthree=isbn.substring(7);
		File f=new File(BOOK+"/"+lastthree);
		File xmlFile=new File(BOOK+"/"+lastthree+"/"+isbn+".xml");
		int i;
		String title="";
		if(!xmlFile.exists()||!f.exists()){
			System.err.println(lastthree+"文件夹或"+isbn+".xml不存在");		
		    
		}else title=getTitle(xmlFile);
		return title;
	}
	public static String getTitle(File f){		
		Document doc=ReadXml.readXml(f.getAbsolutePath());
		Element book=doc.getRootElement();
		Element title=book.element("title");
		return title.getText();
	}
}
