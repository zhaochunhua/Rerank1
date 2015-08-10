package com.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class UserProfile_topic implements Variable_2{
	
	public Map<String,String> ThingId_Isbn(){
		Map<String,String> LT_isbn=new HashMap<String,String>();
		try{
		BufferedReader br=new BufferedReader(new FileReader(new File("resource/amazon-lt.isbn.thingID")));
		String line=null;
		while((line=br.readLine())!=null){
			String[] s=line.split("\t");
			if(!LT_isbn.containsKey(s[1]))
				LT_isbn.put(s[1], s[0]);
		}
		br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return LT_isbn;
	}
	public void topic_15_14() throws IOException{
		int num=0;
		Map<String,String> LT_isbn=ThingId_Isbn();
		
		Document document=ReadXml.readXml(TOPIC_15);
		Element root=document.getRootElement();
		List<Element> listnode=root.elements();
		
		/*PrintStream out = null;
		try {
			out = new PrintStream(new BufferedOutputStream(new FileOutputStream("error.log")),true);//true
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.setOut(out);
		System.setErr(out);*/

		for(Iterator<Element> it=listnode.iterator();it.hasNext();){	
			Document doc = DocumentHelper.createDocument();
			Element topic_u = doc.addElement("topic");
			System.out.println(++num);
			Element topic=it.next();
			String topic_id=topic.attributeValue("id");
			topic_u.addAttribute("id", topic_id);
			
			Element catalog=topic.element("catalog");
			List<Element> listBook=catalog.elements();
			for(Iterator<Element> it_in=listBook.iterator();it_in.hasNext();){
				Element book=it_in.next();
				String LT_id=book.elementText("LT_id").trim();
				//System.out.print(LT_id+";;");
				Element title=book.addElement("title");
				String text_title="";
				if(LT_isbn.containsKey(LT_id)){
					String isbn=LT_isbn.get(LT_id);
					text_title=GetByISBN.getByISBN(isbn);
					//System.out.println(text_title);
					title.addText(text_title);
					topic_u.add((Element)book.clone());
				}else System.err.println("不存在LT_id"+LT_id);
				
			}
			writeXML(doc,topic_id);
		}
		
		/*out.flush();
		out.close();*/
	}
	
	
	public void topic_13_12(){
		Document doc = DocumentHelper.createDocument();
		
		Element root_u = doc.addElement("topics");
		
		File father=new File(TOPIC_13);		
		for(int i=0;i<father.listFiles().length;i++){
			Element topic=root_u.addElement("topic");
			if(father.listFiles()[i].getName().endsWith(".xml"))
			  topic.addAttribute("id", father.listFiles()[i].getName().substring(0, father.listFiles()[i].getName().lastIndexOf('.')));
			else {
				String sss=father.listFiles()[i].getName().substring(father.listFiles()[i].getName().lastIndexOf('.')+1);
				topic.addAttribute("id", sss);
			}
			Document document=ReadXml.readXml(father.listFiles()[i].getAbsolutePath());
			Element root=document.getRootElement();
			Element library=root.element("library");
			List<Element> listBook=library.elements();
			for(Iterator<Element> it=listBook.iterator();it.hasNext();){
				Element book=it.next();
				Element book_u=topic.addElement("book");
				Element LT_id=book_u.addElement("LT_id");				
				Element entry_date=book_u.addElement("entry_date");				
				Element rating=book_u.addElement("rating");				
				Element tags_u=book_u.addElement("tags");
				Element title=book_u.addElement("title");
				
				Element tags=book.element("tags");
				List<Element> tag=tags.elements();
				String text_tags="";
				for(Iterator<Element> it_in=tag.iterator();it_in.hasNext();){
					Element e=it_in.next();
					if(it_in.hasNext())
						text_tags+=e.getText().trim()+", ";
					else text_tags+=e.getText().trim();
											
				}	
				LT_id.addText(book.elementText("lt_work_id").trim());
				entry_date.addText(book.elementText("entry_date").trim());
				rating.addText(book.elementText("rating").trim());
				tags_u.addText(text_tags);
				title.addText(book.elementText("title").trim());				
			}
				
		}
			writeXML(doc,"13");
	
	}
    public void writeXML(Document doc,String name){
    	OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("utf-8");
    	try{
			File threedir = new File(USER_PROFILE);
			if(!threedir.exists()){
				threedir.mkdirs();
			}
			XMLWriter writer = new XMLWriter(new FileWriter(new File(threedir+"/15",name+".xml")),format);
			System.out.println("hhh");
			writer.write(doc);
			writer.flush();
			writer.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}	
		
	}
    public static void main(String args[]) throws IOException{
    	UserProfile_topic t=new UserProfile_topic();
    	t.topic_15_14();
    	//t.topic_13_12();
    	
    	
    }
}
