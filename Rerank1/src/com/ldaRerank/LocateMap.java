package com.ldaRerank;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.text.html.HTMLDocument.Iterator;

public class LocateMap {
	public Map<String, Integer> locmap(String path){
		Map<String,Integer> map=new HashMap<String,Integer>();
		File f=new File(path);
		for(int i=0;i<f.listFiles().length;i++){
			File son=f.listFiles()[i];
			map.put(son.getName(), i);
		}
		return map;
	}
/*	public static void main(String args[]){
		LocateLine l=new LocateLine();
		Map<String, Integer> map=l.locate("F:\\work\\LDAGibbsSampling\\data\\LdaOriginalDocs");
		for(java.util.Iterator<Entry<String, Integer>> it=map.entrySet().iterator();it.hasNext();){
			Entry<String, Integer> e=it.next();
			System.out.println(e.getKey()+"   "+e.getValue());
		}
			
		}*/

}
