package com.rerank2;

import org.dom4j.Document;
import org.dom4j.Element;

import com.util.ReadXml;

public class NewTopicXML implements PathName{
	Document document=ReadXml.readXml(TOPIC_15);
	public void newtopic(){
		Element e=document.getRootElement();
		
	}

	public static void main(String args[]){
		NewTopicXML t=new NewTopicXML();
		t.newtopic();
	}
}
