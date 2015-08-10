package com.util;

public class RmSpec {
	public static String Rm(String line){
		char[] spec={'\"',':',',','.','?',';','!','~','_','|','@','+','=','<','>','(',')','{','}','[',']','/'};
		for(int q=0;q<spec.length;q++){
			line=line.replace(spec[q], ' ');
		}
		return line;
	}
	public static String Rm2(String line){
		char[] spec={'\"',':','.','?',';','!','~','_','|','@','+','=','<','>','(',')','{','}','[',']','/'};
		for(int q=0;q<spec.length;q++){
			line=line.replace(spec[q], ' ');
		}
		return line;
	}

}
