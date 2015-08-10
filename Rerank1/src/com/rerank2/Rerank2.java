package com.rerank2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.dao.BaseDao_Con;
import com.main.FirstRankList;
import com.outputResult.OutPut;
import com.relevance.CosSim;
import com.relevance.TwoMatrixSim;
import com.relevance.VectorSim;
import com.util.BookVector;
import com.util.SortMapDouble;
import com.util.SortMapFloat;
import com.util.Variable;
import com.util.Variable_2;

public class Rerank2 implements Variable_2{
	public static Connection con=(new BaseDao_Con()).getConnection();
	
	public void rerank2(String outputpath,String rerankFile) throws IOException, SQLException{
		ArrayList<String[]> list=FirstRankList.getList(outputpath);
		int i,j=0,top_k_user=10,top_k_book=100;
		List<String> rerank=new ArrayList<String>();
		int sss=0;
		
		while(j<list.size()){
			i=j;
			String id=list.get(i)[0];
			SimUser u=new SimUser();
			Map<String,Double> simUser=u.simUser(id,top_k_user);
			Map<String,Double> rerankBookScore=new HashMap<String,Double>();
			do{
				
				String isbn=list.get(j)[1];
				double simBookScore=0.0;
				for(Iterator<Entry<String,Double>> it=simUser.entrySet().iterator();it.hasNext();){
					Entry<String,Double> e=it.next();
					String topic_id=e.getKey();
					double simUserScore=e.getValue();
					
					Map<String,Double> book_map=getQrelsBook(topic_id);
					simBookScore+=simToOneHistory(isbn, book_map)*simUserScore;
				}
				System.out.println((++sss)+"；isbn"+isbn+",score:"+simBookScore);
				rerankBookScore.put(isbn, simBookScore);
				j++;
				
			}while((j<list.size()-1&&list.get(j+1)[0].equals(id))||j==list.size()-1);
			List<Map.Entry<String,Double>> sortList=(new SortMapDouble()).sortList(rerankBookScore, top_k_book);
			for(int ii=0;ii<sortList.size();ii++){
				Map.Entry<String,Double> e=sortList.get(ii);
				//28818 Q0 0760708401 20 -5.3563104 galago
				rerank.add(id+" Q0 "+e.getKey()+" "+ii+" "+e.getValue()+" galago");
			}
		}
		(new OutPut()).outPut(rerank,rerankFile);
		con.close();
	}
	public double simToOneHistory(String isbn,Map<String,Double> book_map){
		//cosSim*qrels' score
		double score = 0.0;
		for(Iterator<Entry<String,Double>> it=book_map.entrySet().iterator();it.hasNext();){
			Entry<String,Double> e=it.next();
			double score_1=CosSim.simValue(BookVector.bookVector(e.getKey()),BookVector.bookVector(isbn));
			score+=score_1*e.getValue();
		}
		System.out.println("~~~~~~"+score);
		return score;
		
	}
	public Map<String,Double> getQrelsBook(String topic_id) throws IOException{
		BufferedReader br=new BufferedReader(new FileReader(new File(ALL_QRELS)));
		String line=null;
		Map<String,Double> map=new HashMap<String,Double>();
		while((line=br.readLine())!=null){
			String[] s=line.split(" ");
			if(s[0].equals(topic_id))
			   map.put(s[2], Double.valueOf(s[3]));
		}
		br.close();
		return map;
	}
	public static void main(String args[]) throws IOException, SQLException{
		Rerank2 rerank2=new Rerank2();
		rerank2.rerank2(FIRSTRANK,RERANK+"/rerank2");
	}

}
