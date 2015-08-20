package com.rerank2;

import java.io.File;
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
import com.util.UserMatric;
import com.util.Variable_2;

public class SimUser implements Variable_2{
	
	/**
	 * return map<topic_id,similary score>
	 */
	//get the the simlary users for user(whoes user_id=id) 
	public Map<String,Double> simUser(String id,int k){
		
		if (UserMatric.list_vector.size()==0)
		   UserMatric.TopicMatrix();
		int i,j;
		Map<String,Double> map=new HashMap<String,Double>();
		for(i=0;i<UserMatric.topic_ids.size();i++){
			if(UserMatric.topic_ids.get(i).equals(id))
				break;
		}
		for(j=0;j<UserMatric.list_vector.size();j++){
			if(j==i) continue;
			else  map.put(UserMatric.topic_ids.get(j), CosSim.simValue(UserMatric.list_vector.get(i), UserMatric.list_vector.get(j)));
		}
		Map<String,Double> sortMap=SortMapDouble.sortMap(map,k);
		System.out.println("get the SimUsers");
		return sortMap;
	}
	public  static void main(String args[]){
		SimUser in=new SimUser();
		in.simUser("57",10);
		
	}
}
