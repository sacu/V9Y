package org.jiira.utils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jiira.data.mybatis.pojo.User;
import org.jiira.data.mybatis.service.IUserService;
import org.jiira.utils.CommandCollection.AccountTypeEnum;

public class CacheCollection {
	// 用户
	private Map<Integer, User> usersByID;// id : key 信息获取
	private Map<String, User> usersByName;// account : key 登录
	private Map<String, User> usersByNickName;// account : key 登录

	private static CacheCollection instance;

	public synchronized static CacheCollection getInstance() {
		if (instance == null) {
			instance = new CacheCollection();
		}
		return instance;
	}
	
	/**
	 * 数据初始化
	 */
	public void readCache() {
		// 用户数据
		IUserService ius = MITSF.getIService(IUserService.class);
		List<User> us = ius.getUsers();

		usersByID = new ConcurrentHashMap<Integer, User>();
		usersByName = new ConcurrentHashMap<String, User>();
		usersByNickName = new ConcurrentHashMap<String, User>();
		for (User user : us) {
			usersByID.put(user.getId(), user);
			usersByName.put(user.getUserName(), user);
			usersByNickName.put(user.getNickName(), user);
			user.setState(AccountTypeEnum.Offline);
		}
	}
	
	
	/// ###############################################################
		/// 用户表
		/// ###############################################################
		/**
		 * 通过userName（帐号）获取用户信息
		 * 
		 * @param userName
		 * @return
		 */
		public User getUserModelByName(String u) {
			if (usersByName.containsKey(u)) {
				return usersByName.get(u);
			}
			return null;
		}

		public User getUserModelByID(Integer userID) {
			if (usersByID.containsKey(userID)) {
				return usersByID.get(userID);
			}
			return null;
		}
		
		public User getUserModelByNickName(String nickName){
			if (usersByNickName.containsKey(nickName)) {
				return usersByNickName.get(nickName);
			}
			return null;
		}

		public void addUserModel(User u) {
			usersByID.put(u.getId(), u);
			usersByName.put(u.getUserName(), u);
			usersByNickName.put(u.getNickName(), u);
			u.setState(AccountTypeEnum.Offline);
		}
}
