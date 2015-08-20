package com.dao;

import java.util.ArrayList;
import java.util.List;

import com.main.Rerank;
import com.persistance.Book;

public class BookDao extends BaseDao {
	public List<Book> book_tags(String file) {
		List<Book> list = new ArrayList<Book>();
		String sql = "select * from tagfile where file=?";
		try {
			//super.getConnection();
			pstm = Rerank.con.prepareStatement(sql); 
			pstm.setString(1, file);
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				Book b = new Book();
				b.setTag(rs.getString("tag"));
				b.setFile(rs.getString("file"));
				b.setTag_count(rs.getInt("tag_count"));
				
				list.add(b);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
