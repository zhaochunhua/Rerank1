package com.util;

import java.io.File;


public class RenameFile {
	public void rename(String path){
		int i=0;
		for(File docFile : new File(path).listFiles()){
			docFile.renameTo(new File(path+"/"+docFile.getName().substring(docFile.getName().length()-3)));
			System.out.println(i++);
		}
	}
	public static void main(String args[]){
		RenameFile r=new RenameFile();
		r.rename("H:\\book\\xmlChangeNewNew");
	}

}
