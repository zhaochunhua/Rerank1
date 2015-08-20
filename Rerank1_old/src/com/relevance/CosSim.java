package com.relevance;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Element;

import com.dao.BookDao;
import com.dao.UserDao;
import com.persistance.Book;
import com.persistance.User;
import com.util.ReadXml;
import com.util.RemoveStop;
import com.util.RmSpec;
import com.util.TagFilterQ;
import com.util.Variable;

public class CosSim {
	/**
	 * @param ui  Map<term,term_frenquency>
	 * @param uj
	 * @return
	 */
	public static double simValue(Map<String,Double> ui,Map<String,Double> uj){
		double score=0,dotProduct=0,di=0,dj=0;
		for(Iterator<Entry<String, Double>> it=ui.entrySet().iterator();it.hasNext();){
			Entry<String,Double> e=it.next();
			di+=e.getValue()*e.getValue();
			if(uj.containsKey(e.getKey()))
				dotProduct+=e.getValue()*uj.get(e.getKey());
		}
		for(Iterator<Entry<String, Double>> it=uj.entrySet().iterator();it.hasNext();){
			Entry<String,Double> e=it.next();
			dj+=e.getValue()*e.getValue();
		}
		if(di!=0.0&&dj!=0.0){
			score=dotProduct/Math.sqrt(di*dj);
		}		
		else score=0.0;
		return score;			
	}
}
