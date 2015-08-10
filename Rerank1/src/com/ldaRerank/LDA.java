package com.ldaRerank;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.firstRank.FirstRankBook_no_duplicates;
import com.firstRank.FirstRankList;
import com.util.SortMapDou;
import com.util.SortMapDouble;
import com.util.SortMapFloat;
import com.util.Variable;
import com.util.Variable_2;

public class LDA {
	double [][] theta;   //the matric of the book topic 
	double [][] user;
	Map<String, Integer> mapU=new HashMap<String,Integer>();// userid and the line of the userid
	Map<String, Integer> mapB=new HashMap<String,Integer>();
	Map<String, Double> rerank=new HashMap<String,Double>();
	
	double parameter=0.5f;//the weight of the simsore
	public void simScore(){
		twoMatric();//initiate the mapU , mapB and theata ,user matric
		List<String[]> list=(new FirstRankList()).getList(Variable_2.FirstRank_n);
		File f ;
		if(( f=new File(Param.LDARerank)).exists()){
			f.delete();
			System.out.println("delete");
		}
		for(int i=0;i<list.size();i++){
			String [] s=list.get(i);
			Integer uid = null;
			Integer bid=null;
			if(mapU.containsKey(s[0]))
				uid=mapU.get(s[0]);
			if(mapB.containsKey(s[1]))
				bid=mapB.get(s[1]);
			//computer sim value
			double score=0;
			if(uid!=null&&bid!=null)
				for(int p=0;p<Param.TopicNum;p++){
					score+=user[uid][p]*theta[bid][p];
				}
			else System.err.println("the map of user or book is null");
			//computer the composite score
			score=(1-parameter)*Double.parseDouble(s[2])+parameter*score;
			rerank.put(s[0]+" "+s[1], score);
			if(rerank.size()==100){
				Map newmap=(new SortMapDou()).sortMap(rerank);				
				sysRerank(newmap);
				rerank.clear();
			}
		}
		
		
	}
	public void sysRerank(Map map){  
	
		try {
			BufferedWriter   bw=new   BufferedWriter(new  FileWriter(Param.LDARerank,true));
		    int i=0;
		for(Iterator<Entry<String,Double>> it=map.entrySet().iterator();it.hasNext();){
			Entry<String,Double> e=it.next();
			//1116 Q0 1020268 21 -263.28939819 galago
			bw.write(e.getKey().split(" ")[0]+" Q0 "+e.getKey().split(" ")[1]+" "+i+++" "+e.getValue()+" galago");
			bw.newLine();
		}
		bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void twoMatric(){
		FirstRankBook_no_duplicates firankbook=new FirstRankBook_no_duplicates();
		List<String> listbook=firankbook.bookList(Variable_2.CORPUS,Variable_2.FirstRank_n);
			String line="";
		int i=-1;
		int n=-1;
		int M=listbook.size();
		int K=Param.TopicNum;
		int U=Param.UserNum;
		 theta = new double[M][K];   //the matric of the book topic 
		 user = new double[U][K];   //the matric of the book topic 
		try {
			BufferedReader br=new BufferedReader(new FileReader(new File(Param.LdaResultsPath+"lda_100.theta")));
			while((line=br.readLine())!=null){
				
				if(line.startsWith("b")){
					i++;
					String[] ss=line.split("\t");
					if(ss.length!=K+1)
						System.err.println("the number of the topic isn't the same with the theta file!");
					else {
						mapB.put(ss[0].substring(1), i);
						for(int j=0;j<K;j++){
						theta[i][j]=Float.parseFloat(ss[j+1]);
						}
					}
				}else if(line.startsWith("u")){
					n++;
					String[] ss=line.split("\t");
					mapU.put(ss[0].substring(1), n);
					for(int j=0;j<K;j++){
						user[n][j]=Double.parseDouble(ss[j+1]);
					}
				}
			}                      
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	public static void main(String args[]){
		LDA lda=new LDA();
		lda.simScore();
	}

}
