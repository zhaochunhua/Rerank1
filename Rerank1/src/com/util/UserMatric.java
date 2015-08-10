package com.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

public class UserMatric implements Variable_2{
	public static List<String> topic_ids=new ArrayList<String>();	
	public static List<Map<String,Double>> list_vector=new ArrayList<Map<String,Double>>();
	
	public static List<Map<String,Double>> TopicMatrix(){
		File f=new File(USER_PROFILE);
		String topic_id=null;
		for(int i=0;i<f.listFiles().length;i++){
			File son=f.listFiles()[i];
			Map<String,Double> map=new HashMap<String,Double>();
			Document document=ReadXml.readXml(son.getAbsolutePath());
			Element root=document.getRootElement();
			topic_id=root.attributeValue("id");
			topic_ids.add(topic_id);
			List<Element> listnode=root.elements();
			for(Iterator<Element> it_in=listnode.iterator();it_in.hasNext();){
				Element book=it_in.next();
				String title_text=book.elementText("title");
				String[] title=RemoveStop.filterStopWord(RmSpec.Rm(title_text)).split(" ");
				Double rating=Double.valueOf(book.elementText("rating"));
				String[] tag=RemoveStop.filterStopWord(RmSpec.Rm2(book.elementText("tags"))).split(",");
				String[] term=UnionTwoList.unionList(title,tag);
				for(int j=0;term!=null&&j<term.length;j++){
					if(!map.containsKey(term[j]))
						map.put(term[j], rating-5.0);
					
					else map.put(term[j], map.get(term[j])+rating-5.0);
				}
			}
			
			list_vector.add(map);
			System.out.println(list_vector.size());
			
			
		}
		System.out.println("get the Users matric");
		return list_vector;
	}
	

}
