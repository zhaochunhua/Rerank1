package com.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.junit.Test;

import com.util.Variable;

public class UserSave {
	@Test
	public void userSave(){
		
	}
	@Test
	public void RandomAccessFileTest() throws IOException{
		File f=new File("F:/work-zhaochunhua/SBS/userlist.txt");
		RandomAccessFile infile=new RandomAccessFile(f,"rw");
		   if(f.exists()){
			   System.out.println("exist");
		    infile.seek(1);
		   }
		   String ss=infile.readLine();
		   System.out.println("ss:"+ss);
		   infile.close();
	}
	@Test
	public void cutOutput() throws IOException{
		BufferedReader br=new BufferedReader(new FileReader(Variable.outputpath));
		
		StringBuffer content=new StringBuffer();
		String id=null,line=null;
		int i=-1,num=100;
		while((line=br.readLine())!=null){
			
			String id_new=line.split(" ")[0];
			if(++i==0){
				id=id_new;
			}
			if(i<num&&id_new.equals(id)){
				content.append(line+"\n");
			}
			if(!id_new.equals(id)){
				content.append(line+"\n");
				i=0;
			}
			id=id_new;
		}
		br.close();
		BufferedWriter bw=new BufferedWriter(new FileWriter(Variable.outputpath));
		bw.write(content.toString());
		bw.close();
		
	}
	

}
