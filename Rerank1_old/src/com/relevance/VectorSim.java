package com.relevance;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import com.dao.BookDao;
import com.dao.UserDao;
import com.persistance.Book;
import com.persistance.User;
import com.util.ReadXml;
import com.util.RemoveStop;
import com.util.RmSpec;
import com.util.TagFilterQ;
import com.util.Variable;

public class VectorSim {
	public static float calQueryNum(String userid){
		org.dom4j.Document document=ReadXml.readXml(Variable.pathTopic) ;
		Element root=document.getRootElement();
		List listnode=root.elements();
		String query = "";
		for(Iterator it=listnode.iterator();it.hasNext();){
			Element topic=(Element)it.next();
			String id=topic.attributeValue("id");
			if(id.equals(userid)){
				query=topic.elementText("query")+" "+topic.elementText("title")+" "+
				topic.elementText("group")+" "+topic.elementText("narrative");	
				break;
			}			
		}	
		float termNum_q=RmSpec.Rm(RemoveStop.filterStopWord(query.toLowerCase())).split(" ").length;
		return termNum_q;
	}
	public static float relValueBU(String bookid,String userid){
		List<Book> listB=(new BookDao()).book_tags(bookid);
	    
	    float l=0;
	    
	    for(Iterator<Book> it=listB.iterator();it.hasNext();){
	    	Book book=it.next();
	    	User user=(new UserDao()).searchUser(userid, book.getTag());
	    	if(user.getProfile()!=null){
	    		l+=((float)book.getTag_count())/((float)listB.size())*((float)user.getTag_count()/(float)user.getSource_count());	    		
	    	    /*if(Float.isNaN(l)){
	    	    	System.out.print("?????");
	    	    }*/
	    	}
	    }
	    
		return l/calQueryNum(userid);
		
	}
	public static float relValueBQ13(String bookid,String topicid){
		org.dom4j.Document document=ReadXml.readXml(Variable.pathTopic) ;
		Element root=document.getRootElement();
		List listnode=root.elements();
		String query = "";
		for(Iterator it=listnode.iterator();it.hasNext();){
			Element topic=(Element)it.next();
			String id=topic.attributeValue("id");
			if(id.equals(topicid)){
				query=topic.elementText("query")+" "+topic.elementText("title")+" "+
				topic.elementText("group")+" "+topic.elementText("narrative");	
				break;
			}			
		}	
		float w= 0;
		float relevance= 0;
		float termNum_q=RmSpec.Rm(RemoveStop.filterStopWord(query.toLowerCase())).split(" ").length;

		float parameter= 0.5f;
		Map<String,Integer> topic=(new TagFilterQ()).tagFilter(query);
	    float l=0;
	    float k=0;
	    List<Book> listB=(new BookDao()).book_tags(bookid);
	    for(Iterator<Book> it=listB.iterator();it.hasNext();){
	    	Book book=it.next();
	    	if(topic.containsKey(book.getTag())){
	    		w+=book.getTag_count();
	    		k++;
	    	}
	    }
	    relevance= (float) (w/termNum_q*(Math.pow(k/termNum_q, parameter)));
		return relevance;	    	
	}	
}
