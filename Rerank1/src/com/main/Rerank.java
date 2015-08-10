package com.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.dao.BaseDao;
import com.dao.BaseDao_Con;
import com.relevance.VectorSim;
import com.util.SortMapFloat;
import com.util.Variable;

public class Rerank{
	public static Connection con=(new BaseDao_Con()).getConnection();
	public void relRerank(String outputpath)  throws Exception {
		//计算userid所有的relevance，并提出top-k提交结果
		int n=0;
		ArrayList<String[]> list=FirstRankList.getList(outputpath);
		BufferedWriter bw=new BufferedWriter(new FileWriter(Variable.rerankpath));
		String id=list.get(0)[0];
		float parameter=0.5f;
		Map<String,Float> map=new HashMap<String,Float>();
		for(int i=0;i<list.size();i++){
			System.out.println(i);
			if(list.get(i)[0].equals(id)){
				float score1=0;
				score1=VectorSim.relValueBU(list.get(i)[1], list.get(i)[0]);
				float score2=VectorSim.relValueBQ13(list.get(i)[1], list.get(i)[0]);
				float score=parameter*Float.parseFloat(list.get(i)[2])+(1-parameter)*(score1+score2)/2;
				map.put(list.get(i)[0]+" "+list.get(i)[1], score);	
				
			}else{
				Map newmap=SortMapFloat.sortMap(map);
				int num=1;
				for(Iterator it=newmap.entrySet().iterator();it.hasNext();){
					Map.Entry<String, Float> entry=(Map.Entry<String, Float>)it.next();
					String s=entry.getKey().split(" ")[0]+" Q0 "+entry.getKey().split(" ")[1]+" "+
					num+++" "+entry.getValue()+" galago\n";
					bw.write(s);
				}
				map.clear();
				id=list.get(i--)[0];
			}
			
			
		}
		bw.close();	
		con.close();
	}
	public static void main(String args[]) throws Exception{
		//step1 galago query
		/*CreateQueryParamGalago13 query=new CreateQueryParamGalago13();
		try {
			query.createQueryParam( "F:\\SBS-En-1\\data\\quey-param-sbs\\inex13sbs.SDM.json");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//step2
		String outputpath=Variable.outputpath;
		(new Rerank()).relRerank(outputpath);
		/*for(Iterator it=map.entrySet().iterator();it.hasNext();){
			Map.Entry<String,Float> entry=(Map.Entry<String,Float>)it.next();
			System.out.println(entry.getKey());
		}*/
	}

}
