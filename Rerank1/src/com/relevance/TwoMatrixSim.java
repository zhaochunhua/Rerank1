package com.relevance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.rerank2.SimUser;

public class TwoMatrixSim extends CosSim{
	/**
	 * 
	 * @param list1
	 * @param list2
	 * @return List:vector(cosSim1,cosSim2,cosSim3……)
	 */
	public List twoMatrixSim(List<Map<String,Double>> list1,List<Map<String,Double>> list2){
		int i,j;
		List l1=new ArrayList();
		for(i=0;i<list1.size();i++){
			List<Double> l2=new ArrayList<Double>();
			Map<String,Double> map1=list1.get(i);
			for(j=0;j<list2.size();j++){
				Map<String,Double> map2=list2.get(j);
				Double score=super.simValue(map1, map2);
				l2.add(score);
			}
			l1.add(l2);
		}
		return l1;
	}
	public  static void main(String args[]){
		TwoMatrixSim two=new TwoMatrixSim();
		List<Map<String,Double>> list_vector1=new ArrayList<Map<String,Double>>();
		List<Map<String,Double>> list_vector2=new ArrayList<Map<String,Double>>();
		
		Map<String,Double> mapi=new HashMap<String,Double>();
		mapi.put("english", 2.0);
		mapi.put("math", 5.5);
		mapi.put("history", 1.0);
		Map<String,Double> mapj=new HashMap<String,Double>();
		mapj.put("math", 5.5);
		mapj.put("history", 1.0);
		mapj.put("chemtor", 8.8);
		Map<String,Double> mapk=new HashMap<String,Double>();
		mapk.put("math", 5.5);
		mapk.put("history", 1.0);
		mapk.put("english", 8.8);
		Map<String,Double> mapn=new HashMap<String,Double>();
		mapn.put("mat", 5.5);
		mapn.put("histor", 1.0);
		mapn.put("chemto", 8.8);
		
		list_vector1.add(mapi);
		list_vector1.add(mapj);
		list_vector1.add(mapk);
		list_vector1.add(mapn);
		list_vector2.add(mapi);
		list_vector2.add(mapj);
		list_vector2.add(mapk);
		list_vector2.add(mapn);
		//double s=CosSim.simValue(list_vector.get(0), list_vector.get(1));
		System.out.println(two.twoMatrixSim(list_vector1, list_vector2));
		
	}
}
