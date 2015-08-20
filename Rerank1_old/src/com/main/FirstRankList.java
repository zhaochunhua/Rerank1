package com.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FirstRankList {
	/**
	 * 
	 * @param outputpath  each line of the outputpath is 
	 * 28818 Q0 0882847325 7 -11.53331212 galago
	 * @return ArrayList<String[]> which put "28818 0882847325 -11.53331212"
	 */
	public static ArrayList<String[]> getList(String outputpath){
		ArrayList<String[]> list=new ArrayList<String[]>();
		try {
		BufferedReader br = new BufferedReader(new FileReader(outputpath));
		String line=null;
		while((line=br.readLine())!=null){
			String[] output=line.split(" ");
			String[] s=new String[3];
			s[0]=output[0];
			s[1]=output[2];
			s[2]=output[4];
			list.add(s);
		}
		br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;		
	}

}
