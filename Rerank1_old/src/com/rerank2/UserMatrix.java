package com.rerank2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Document;
import org.dom4j.Element;

import com.relevance.CosSim;
import com.util.ReadXml;
import com.util.RemoveStop;
import com.util.RmSpec;
import com.util.SortMapDouble;
import com.util.SortMapFloat;
import com.util.UnionTwoList;
import com.util.Variable_2;

public class UserMatrix implements Variable_2{
	public List<String> topic_ids=new ArrayList<String>();	
	public List<Map<String,Double>> list_vector=new ArrayList<Map<String,Double>>();
	
	public List<Map<String,Double>> userMatrix(){
		Document document=ReadXml.readXml(TOPIC_ALL);
		Element root=document.getRootElement();
		List<Element> listnode=root.elements();
		String topic_id=null;
		for(Iterator<Element> it=listnode.iterator();it.hasNext();){
			Map<String,Double> map=new HashMap<String,Double>();
			Element topic=it.next();
			topic_id=topic.attributeValue("id");
			topic_ids.add(topic_id);
			List<Element> listBook=topic.elements();
			for(Iterator<Element> it_in=listBook.iterator();it_in.hasNext();){
				Element book=it_in.next();
				String title_text=book.elementText("title");
				String[] title=RemoveStop.filterStopWord(RmSpec.Rm(title_text)).split(" ");
				Double rating=Double.valueOf(book.elementText("rating"));
				String[] tag=RemoveStop.filterStopWord(RmSpec.Rm2(book.elementText("tags"))).split(",");
				String[] term=UnionTwoList.unionList(title,tag);
				for(int i=0;term!=null&&i<term.length;i++){
					if(!map.containsKey(term[i]))
						map.put(term[i], rating-5.0);
					
					else map.put(term[i], map.get(term[i])+rating-5.0);
				}
			}
			list_vector.add(map);
		}
		return list_vector;
	}
	
	public  static void main(String args[]){
		UserMatrix in=new UserMatrix();
		
	}
}
