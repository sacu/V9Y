package org.jiira.data.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jiira.data.mybatis.pojo.User;
public interface IUserDao {
	
	/**
	 * 获取用户
	 */
	public User getUser(@Param("userName")String userName, @Param("passWord")String passWord);
	public List<User> getUsers();
	
}