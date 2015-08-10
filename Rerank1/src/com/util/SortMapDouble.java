package com.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
/**
 * 对无序的map依据value值进行从大到小排序
 * @author Administrator
 *
 */
public class SortMapDouble {
    public static Map sortMap(Map oldMap,int k) {
        ArrayList<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String, Double>>(oldMap.entrySet());
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {

            @Override
            public int compare(Entry<java.lang.String, Double> arg0,
                    Entry<java.lang.String, Double> arg1) {
            	if((arg1.getValue() - arg0.getValue())>0)
            		return 1;
            	else if((arg1.getValue() - arg0.getValue())==0)
            		return 0;
            	else return -1;
            }
        });
       
        Map newMap = new LinkedHashMap();
        if(k<list.size())
        	for (int i = 0; i < k; i++) {
                newMap.put(list.get(i).getKey(), list.get(i).getValue());
            }
        
        else 
        	for (int i = 0; i < list.size(); i++) {
            newMap.put(list.get(i).getKey(), list.get(i).getValue());
        }
        return newMap;
    }
    public ArrayList<Map.Entry<String, Double>> sortList(Map oldMap,int k) {
        ArrayList<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String, Double>>(oldMap.entrySet());
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {

            @Override
            public int compare(Entry<java.lang.String, Double> arg0,
                    Entry<java.lang.String, Double> arg1) {
            	if((arg1.getValue() - arg0.getValue())>0)
            		return 1;
            	else if((arg1.getValue() - arg0.getValue())==0)
            		return 0;
            	else return -1;
            }
        });
       
        if(list.size()>k){
        	for(int i=k;i<list.size();i++){
        		list.remove(i);
        	}
        }
        return list;
    }
    
}
