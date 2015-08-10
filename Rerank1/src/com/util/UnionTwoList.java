package com.util;

public class UnionTwoList {

	public static String[] unionList(String[] a,String[] b){
		String[] union=null;
		if(a[0].equals("")&&!b[0].equals(""))
			union=b;
		else if(!a[0].equals("")&&b[0].equals(""))
			union=a;
		else if(!a[0].equals("")&&!b[0].equals("")){
			union=new String[a.length+b.length];
			for(int i=0;i<a.length;i++)
				union[i]=a[i];
			
			for(int j=0;j<b.length;j++)
				union[a.length+j]=b[j];
		}
		return union;		
	}

}
