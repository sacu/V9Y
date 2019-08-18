package org.jiira.data.mybatis.service;

import java.util.List;

import org.jiira.data.mybatis.pojo.User;

public interface IUserService {
	/**
	 * 获取管理员信息
	 * @param redPacketId
	 * @param unitAmount
	 */
	public User getUser_old(String userName, String passWord);
	public User getUser(String userName, String passWord);
	public List<User> getUsers();
}
