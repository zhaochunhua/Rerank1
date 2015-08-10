package com.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.dao.BookDao;
import com.persistance.Book;

public class BookVector {
	public static Map<String,Double> bookVector(String bookid){
		Map<String,Double> map=new HashMap<String,Double>();
		List<Book> listB=(new BookDao()).book_tags(bookid);
		for(Iterator<Book> it=listB.iterator();it.hasNext();){
			Book book=it.next();
			map.put(book.getTag(),Double.valueOf(book.getTag_count()));			
		}
		return map;
	}
	public static void main(String args[]){
		
	}
}
