package com.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

public class Isbn_title implements Variable_2{
	static String BOOK_TITLE="";
	public static Map<String,String> getTitle() throws IOException{
		Map<String,String> map=new HashMap<String,String>();
		
		BufferedReader br=new BufferedReader(new FileReader(new File(BOOK_TITLE)));
		String line=null;
		while((line=br.readLine())!=null){
			String[] s=line.split("\t");
			if(s.length==2)
			  map.put(s[0], s[1]);
			else map.put(s[0], "");
		}
		br.close();
		return map;
	}
	public void isbn_Title() throws IOException{
		File father=new File("J:/book/xmlChangeNewNew");
		BufferedWriter bw=new BufferedWriter(new FileWriter(new File(BOOK_TITLE)));
		int num=0;
		for(int i=0;i<father.listFiles().length;i++){
			System.out.println("第"+i+"个子文件夹");
			File son=father.listFiles()[i];
			for(int j=0;j<son.listFiles().length;j++){
				System.out.println(num++);
				File grandSon=son.listFiles()[j];
				Document document=ReadXml.readXml(grandSon.getAbsolutePath());
				Element book=document.getRootElement();
				String isbn=book.element("isbn").getText();
				String title=book.element("title").getText();
				bw.write(isbn+"\t"+title+"\n");
			}
		}
		bw.close();
		
	}
	public static void main(String args[]) throws IOException{
		Isbn_title is=new Isbn_title();
		is.isbn_Title();
	}

}
