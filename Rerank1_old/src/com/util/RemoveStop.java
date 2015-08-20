package com.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;



public class RemoveStop {
	public static String stopWordTable="resource/Inquery stopwords.txt";
	public  static Set<String> readStopWordTable() {
		try {
		FileInputStream input=new FileInputStream(stopWordTable);
		BufferedReader ii=new BufferedReader(new InputStreamReader(input));
		String Line;
		Set<String> stopWordSet=new HashSet<String>();
		String stopWord=null;//初始化停用词表
		for (;(stopWord=(String) ii.readLine())!=null;) {
			stopWordSet.add(stopWord);
		}
		ii.close();
		input.close();
		return stopWordSet;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * input a string 
	 * @param stopWordSet
	 * @param str
	 * @return
	 */
	public static String filterStopWord(String str){
		Set<String> stopWordSet=readStopWordTable();
		char single;
		String single2 ="";
		String word="";
		String myStr="";
		str+=" ";
		for(int i=0;i<str.length();i++){
			
			single=str.charAt(i);
			single2=single+"";
			if(single2.matches("[A-Z]"))
				single2=single2.toLowerCase();
			if(!single2.matches("\\s")){
				if(single2.matches("[0-9]")||single2.matches("[a-z]"))
				  word=word+single2;				
			}else{
				String s = "";
			    for (java.util.Iterator it =stopWordSet.iterator(); it.hasNext();) {
				 s = (String) it.next();
				if(word.equals(s)) break;						
			}
			if(!word.equals(s)){
				//this.saveWord(word);
				myStr=myStr+word+" ";
			}
			word="";
			}		
		}
		myStr=myStr.replaceAll("\\s+", " ").trim();
		return  myStr;		
	}
	/*
	 * a word not a few of words
	 */
	public String filterStopWord2(String str){
		Set<String> stopWordSet=this.readStopWordTable();
		String s = "";
	    for (java.util.Iterator it =stopWordSet.iterator(); it.hasNext();) {
		   s = (String) it.next();
		   if(str.equals(s)) return "";		
	    }
	    return str;	
	}
	public String filterStopWord3(String str,Set<String> stopWordSet){
		char single;
		String single2 ="";
		String word="";
		String myStr="";
		str+=" ";
		for(int i=0;i<str.length();i++){
			
			single=str.charAt(i);
			single2=single+"";
			if(single2.matches("[A-Z]"))
				single2=single2.toLowerCase();
			if(!single2.matches("\\s")){
				  word=word+single2;				
			}else{
				String s = "";
			    for (java.util.Iterator it =stopWordSet.iterator(); it.hasNext();) {
				 s = (String) it.next();
				if(word.equals(s)) break;						
			}
			if(!word.equals(s)){
				//this.saveWord(word);
				myStr=myStr+word+" ";
			}
			word="";
			}		
		}
		myStr=myStr.replaceAll("\\s+", " ").trim();
		return  myStr;		
	}
}
