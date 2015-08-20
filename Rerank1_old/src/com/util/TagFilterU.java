package com.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import com.util.RmSpec;

public class TagFilterU {
	
	public Map<String,Integer> tagFilter(String srcXML){
		Map<String,Integer> map=new HashMap<String,Integer>();
		File f=new File(srcXML);		
		org.dom4j.Document document = readXml(f.getAbsolutePath());
		Element root = document.getRootElement();
		List nodeList_li = root.element("library").elements();      //libary-work-tags-tag
		
		for(Iterator it_li=nodeList_li.iterator();it_li.hasNext();){
			Element work=(Element)it_li.next();
			List nodeList = work.element("tags").elements(); 
			for(Iterator it=nodeList.iterator();it.hasNext();){
				Element e=(Element)it.next();
				String tag=e.getText();
				String[] listT=tag.split(";");
				for(int t=0;t<listT.length;t++){
					String tag_s=(new RmSpec()).Rm(listT[t]);
					tag_s=tag_s.toLowerCase();
					if((!tag_s.equals(""))&&(!tag_s.equals(" ")))
						if(!map.containsKey(tag_s))
						    map.put(tag_s, 1);
						else map.put(tag_s, map.get(tag_s)+1);						
				}
				
			}
        }		
		//System.out.println(srcXML);
		return map;
		
	}
	
	/**
	 * the number of the resource that the user tag
	 * @param srcXML
	 * @return
	 */
	public int tagSourceNum(String srcXML){
		Map<String,Integer> map=new HashMap<String,Integer>();
		File f=new File(srcXML);
		org.dom4j.Document document = readXml(f.getAbsolutePath());
		Element root = document.getRootElement();
		List nodeList_li = root.element("library").elements();      //libary-work-tags-tag
		int num=nodeList_li.size();
		return num;
		
	}
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
	
	
	public static void main(String args[]) throws IOException{
		TagFilterU tf=new TagFilterU();
		Map hashmap=tf.tagFilter("F:\\SBS-En-1\\user data");
		java.util.Iterator it = hashmap.entrySet().iterator();
		while(it.hasNext()){
		java.util.Map.Entry entry = (java.util.Map.Entry)it.next();
		//entry.getKey();// 返回与此项对应的键
		//entry.getValue();//返回与此项对应的值
		System.out.println(entry.getKey());
		}
		
	}	
}
