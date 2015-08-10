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
public class SortMapDou {
    private static void printMap(Map map){
        System.out.println("===================mapStart==================");
        Iterator it = map.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry entry = (Map.Entry) it.next();
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        System.out.println("===================mapEnd==================");
    } 

    public static Map sortMap(Map oldMap) {
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
        for (int i = 0; i < list.size(); i++) {
            newMap.put(list.get(i).getKey(), list.get(i).getValue());
        }
        return newMap;
    }
    
}
