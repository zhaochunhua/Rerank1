package com.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import com.util.RemoveStop;
import com.util.RmSpec;

public class TagFilterQ {
	
	public Map<String,Integer> tagFilter(String topic){
		Map<String,Integer> map=new HashMap<String,Integer>();
		topic=RemoveStop.filterStopWord(topic.toLowerCase());
		topic=RmSpec.Rm(topic);
		String[] s=topic.split(" ");
		
		for(int i=0;i<s.length;i++){
			String tag=s[i];
			if((!tag.equals(""))&&(!tag.equals(" ")))
				if(!map.containsKey(tag))
				    map.put(tag, 1);
				else map.put(tag, map.get(tag)+1);							 
			
		}
		return map;
		
	}
	
	public static void main(String args[]) throws IOException{
		TagFilterQ tf=new TagFilterQ();
		String s="writerreaders recommendations writerreaders "
				+ "enjoy snippets conversation bet literary offerings therenow supposed "
				+ "shamelessly plug okunlike ive stuff published real published authors "
				+ "hobby writers suggestion author ill sound cheap thankskami";
		
		System.out.println(tf.tagFilter(s));
		
		
	}

}
