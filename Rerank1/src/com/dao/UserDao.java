package com.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.main.Rerank;
import com.persistance.Book;
import com.persistance.User;
import com.util.TagFilterU;

public class UserDao extends BaseDao {
	public List<User> user_tags(String profile) {
		List<User> list = new ArrayList<User>();
		String sql = "select * from user where profile=?";
		try {
			pstm = Rerank.con.prepareStatement(sql); 
			pstm.setString(1, profile);
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				User u = new User();
				u.setTag(rs.getString("tag"));
				u.setProfile(rs.getString("profile"));
				u.setTag_count(rs.getInt("tag_count"));
				u.setSource_count(rs.getInt("source_count"));
				list.add(u);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public int saveUser(User user){
		int row = -1;
		String sql = "insert into user values(?,?,?,?)";
		try {
			pstm = Rerank.con.prepareStatement(sql);
			pstm.setString(1, user.getTag());
			pstm.setString(2, user.getProfile());
			pstm.setInt(3, user.getTag_count());
			pstm.setInt(4, user.getSource_count());
			
			row = pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return row;
	}
	public int updateUser(User user){//修改
		int row = -1;
		String sql = "update user set tag_count=? source_count=? where profile = ? and tag = ?";
		try {
			pstm = Rerank.con.prepareStatement(sql);
			pstm.setInt(1, user.getTag_count());
			pstm.setInt(2, user.getSource_count());
			pstm.setString(3, user.getProfile());
			pstm.setString(4, user.getTag());
			
			row = pstm.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return row;
	}
	public int exist_tag(User user) {
		int row=-1;
		List<User> list = new ArrayList<User>();
		String sql = "select * from user where profile=? and tag=?";
		try {
			pstm = Rerank.con.prepareStatement(sql); 
			pstm.setString(1, user.getProfile());
			pstm.setString(2, user.getTag());
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				User u = new User();
				u.setTag(rs.getString("tag"));
				u.setProfile(rs.getString("profile"));
				u.setTag_count(rs.getInt("tag_count"));
				
				list.add(u);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(list.size()>0){
			row=1;
		}
		
		return row;
	}
	public User searchUser(String profile,String tag) {
		
		User u = new User();
		String sql = "select * from user where profile=? and tag=?";
		try {
			pstm = Rerank.con.prepareStatement(sql); 
			pstm.setString(1, profile);
			pstm.setString(2, tag);
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				u.setTag(rs.getString("tag"));
				u.setProfile(rs.getString("profile"));
				u.setTag_count(rs.getInt("tag_count"));
				u.setSource_count(rs.getInt("source_count"));			
			}				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return u;
	}	
	
}
