package com.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CutSurplusFirstRankResult {
	public void cut(String outputpath,String re_outputpath) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(outputpath));
		BufferedWriter bw = new BufferedWriter(new FileWriter(re_outputpath));
		String line="";
		int i=0;
		String topicid_i="",topicid_j="";
		while((line=br.readLine())!=null){
			
			if(i==0) topicid_i=topicid_j=line.substring(0,5);			
			else topicid_i=line.substring(0,5);
			
			if(topicid_i.equals(topicid_j))
				if(i<100){
					i++;
					bw.write(line);
					bw.newLine();
					topicid_j=line.substring(0,5);
				}else {
					topicid_j=line.substring(0,5);
					continue;
				}
			else {
				bw.write(line);
				bw.newLine();
				topicid_j=line.substring(0,5);
				i=1;
			}
			
		}
		bw.close();
		br.close();
		
	}
	public static void main(String arg[]) throws IOException{
		CutSurplusFirstRankResult cutr=new CutSurplusFirstRankResult();
		cutr.cut("G:\\rerank data and code\\data\\2013SBS rank\\BSOrg_1000", "G:\\rerank data and code\\data\\2013SBS rank\\BSOrg_100");
	}

}
