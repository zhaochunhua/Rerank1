package com.firstRank;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.util.Variable_2;

public class FirstRankBook_no_duplicates {
	/**
	 * put the bookid in the list, and the bookid is from the firstrank which is no duplicates
	 * @return
	 */
	public List<String> bookList(String corpus,String firstrank){
		ArrayList<String[]> list=FirstRankList.getList(firstrank);
		List<String> list_no=new ArrayList<String>();
		
		Set<String> book_no_duplicates=new HashSet<String>();
		for(int i=0;i<list.size();i++){
			book_no_duplicates.add(list.get(i)[1]);
		}
		for(Iterator<String> it=book_no_duplicates.iterator();it.hasNext();){
			String e=it.next();
			list_no.add(corpus+e.substring(e.length()-3)+"/"+e+".xml");
		}
		return list_no;		
	}
	/*public static void main(String arg[]) throws IOException{
		FirstRankBook_no_duplicates ff=new FirstRankBook_no_duplicates();
		List<String> bookList=ff.bookList("H:/book/xmlChangeNewNew/", "G:\\rerank data and code\\data\\2013SBS rank\\BSOrg_200");
		BufferedWriter bw = new BufferedWriter(new FileWriter("G:/rerank data and code/data/2013SBS rank/test.txt"));
		for(String s : bookList){
			bw.write(s);
			bw.newLine();
		}
		
		bw.close();
	
	}*/

}
