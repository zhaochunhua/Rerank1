package com.dao;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

import com.persistance.User;
import com.util.TagFilterU;
import com.util.Variable;

public class UserToMySql {
	public void userSave(){
		File f=new File(Variable.pathUser);
		UserDao userDao=new UserDao();	
		int num=0;
		for(int i=0;i<f.listFiles().length;i++){
			String path=f.listFiles()[i].getAbsolutePath();
			String name=f.listFiles()[i].getName().substring(0, f.listFiles()[i].getName().lastIndexOf('.'));
			TagFilterU tag=new TagFilterU();
			Map<String,Integer> map=tag.tagFilter(path);
			int source_count=tag.tagSourceNum(path);
			System.out.println(num++);
			for(Iterator it=map.entrySet().iterator();it.hasNext();){
				Map.Entry<String,Integer> entry = (Map.Entry<String,Integer>) it.next();
				User user=new User();
				user.setProfile(name);
				user.setTag(entry.getKey().toString());
				user.setTag_count(entry.getValue());
				user.setSource_count(source_count);
				
				int row=userDao.exist_tag(user);
				if(row>0){
					userDao.updateUser(user);
				}else userDao.saveUser(user);
			}
		}
	}

}
