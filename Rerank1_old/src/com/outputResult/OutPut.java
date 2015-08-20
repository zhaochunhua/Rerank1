package com.outputResult;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.util.Variable_2;

public class OutPut implements Variable_2{
	public void outPut(List<String> rerank,String filePath) throws IOException{
		BufferedWriter bw=new BufferedWriter(new FileWriter(new File(filePath)));
		for(int i=0;i<rerank.size();i++){
			bw.write(rerank.get(i)+"\n");
		}
		bw.close();
	}
}
