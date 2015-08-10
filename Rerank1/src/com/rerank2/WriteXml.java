package com.rerank2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class WriteXml {
	public static String stopWordTable="Inquery stopwords.txt";
	
	public void write(Element element, String name,String sourcetarget) {		
		Document doc = DocumentHelper.createDocument();
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("utf-8");
		Set<String> stopWordSet=readStopWordTable();
		Element root = doc.addElement("book");
		
		Element isbn = root.addElement("isbn");		
		isbn.addText(element.elementText("isbn"));
		
		Element title = root.addElement("title");	
		String title1=element.elementText("title");		
		title1=filterStopWord(stopWordSet,title1);
		title.addText(title1);
		
		/******begin of tags*******/
		Element tags = root.addElement("tags");	
		List nodeList = element.element("tags").elements();
		String tag="";
		for (Iterator it = nodeList.iterator(); it.hasNext();) {
			Element e = (Element) it.next();
			tag=tag+e.getText()+" ";//e.getText()获取e标签中的内容
		}
		if(!tag.equals("")){
			tag=filterStopWord(stopWordSet,tag);
		    tags.addText(tag);
		}
		/******end of tags*******/
		/******begin of summary and content*******/
		Element summary = root.addElement("summary");	
		Element content = root.addElement("content");	
		String summary1="";
		String content1="";
		List nodeList1 = element.element("reviews").elements();
		for (Iterator it = nodeList1.iterator(); it.hasNext();) {
			Element e = (Element) it.next();
			if(e.element("summary")!=null){
				summary1=summary1+e.element("summary").getText()+" ";
			}
			if(e.element("content")!=null){
				content1=content1+e.element("content").getText()+" ";
			}
		}
		
		
		
		if(!summary1.equals("")){
		    summary1=filterStopWord(stopWordSet,summary1);
		    summary.addText(summary1);
		}
		if(!content1.equals("")){
			content1=filterStopWord(stopWordSet,content1);
		    content.addText(content1);
		}
		/******end of summary and content*******/
		/******begin of editorialreviews*******/
		/*Element editorialreviews=root.addElement("editorialreviews");		
		String editorialreviews1="";
		List nodeList2 = element.element("editorialreviews").elements();
		for (Iterator it = nodeList2.iterator(); it.hasNext();) {
			Element e = (Element) it.next();
			if(e.element("content")!=null){
				editorialreviews1=editorialreviews1+e.element("content").getText()+" ";
			}
		}
		
		if(!editorialreviews1.equals("")){
			editorialreviews1=filterStopWord(stopWordSet,editorialreviews1);
			editorialreviews.addText(editorialreviews1);
			//System.out.println(editorialreviews1);
		}*/
		/******end of editorialreviews*******/
		
		
		/*Element tags=element.element("tags");
		root.add((Element)tags.clone()); //将tags中的所有内容都赋值到目的tags中*/
		
		
		try{
			File threedir = new File(sourcetarget);
			if(!threedir.exists()){
				threedir.mkdirs();
			}else{
			//XMLWriter writer = new XMLWriter(new FileWriter(new File(praentFile.getAbsolutePath(),"D:/xml/"+book.getName_subject()+".xml")),format);
			//File new_three = new File(threedir,book.getBookid()+".xml");
			//FileWriter fw = new FileWriter(new_three);
			XMLWriter writer = new XMLWriter(new FileWriter(new File(threedir,name+".xml")),format);
			writer.write(doc);
			writer.flush();
			writer.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	
	public   Set<String> readStopWordTable() {
		try {
		FileInputStream input=new FileInputStream(stopWordTable);
		BufferedReader ii=new BufferedReader(new InputStreamReader(input));
		String Line;
		Set<String> stopWordSet=new HashSet<String>();
		String stopWord=null;//初始化停用词表
		for (;(stopWord=(String) ii.readLine())!=null;) {
			stopWordSet.add(stopWord);
		}
		ii.close();
		input.close();
		return stopWordSet;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @param stopWordSet
	 * @param s
	 * @return
	 * 去停用词
	 * 1.要有一个停用词表，可以网上下一个
	 * 2.把停用词表读入一个集合;
	 * 3.把要处理的字符串，以单个字符的形式读到一个char*指向的空间里，当读到不是字母的就停止，这样这个char*指向的空间里就存下了一个单词
	 * 4.把这个char *指针赋给一个string str变量，让这个str与每一个停用词比较。
	 * 5.如果不是停用词就把这个单词追加到另一个字符串string str1里，
	 * 6.如果是停用词就不管它了
	 */
	public String filterStopWord(Set stopWordSet,String str){
		char single;
		String sin ="";
		String word="";
		String myStr="";
		Pattern plow,pupper;
		Matcher mlow,mupper;
		boolean blow,bupper;
		for(int i=0;i<str.length();i++){
			single=str.charAt(i);
			sin=single+"";
			/****匹配小写字母和数字*****/
			plow = Pattern.compile("[a-z0-9']");
			mlow = plow.matcher(sin);
			blow = mlow.matches();
			/****匹配大写字母*****/
			pupper = Pattern.compile("[A-Z]");
			mupper = pupper.matcher(sin);
			bupper = mupper.matches();
			
			if(blow){
				word=word+sin;
			}else if(bupper){
				sin=sin.toLowerCase();
				word=word+sin;
			}else {  //遇到空格或特殊符号时 与停用此表匹配
				if(word.length()>2&&i!=str.length()-1){
					String s = "";
				for (Iterator it =stopWordSet.iterator(); it.hasNext();) {
					 s = (String) it.next();
					if(word.equals(s)) break;						
				}
				if(!word.equals(s)){
				myStr=myStr+word+" ";
				}
				}
				word="";
			}
				
			if(i==str.length()-1){//遍历字符串结束时与停用此表匹配
				if(word.length()>2){
					String s = "";
					for (Iterator it =stopWordSet.iterator(); it.hasNext();) {
						 s = (String) it.next();
						if(word.equals(s)) break;						
					}
					if(!word.equals(s)){
					myStr=myStr+word;
					}
					}
					word="";
			}
			
		}
		
		return  myStr;
		
	}

}
