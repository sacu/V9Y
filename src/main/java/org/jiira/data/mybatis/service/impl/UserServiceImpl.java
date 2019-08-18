package org.jiira.data.mybatis.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.jiira.data.mybatis.dao.IUserDao;
import org.jiira.data.mybatis.pojo.User;
import org.jiira.data.mybatis.service.IUserService;
import org.jiira.utils.CommandCollection;
import org.jiira.utils.DBSessionFactory;

import core.utils.SALog;

public class UserServiceImpl implements IUserService {
	@Override
	public User getUser_old(String userName, String passWord) {
		// TODO Auto-generated method stub
		SqlSession session = DBSessionFactory.getSqlSessionFactory(CommandCollection.DB_ID_BASETABLE)
				.openSession(CommandCollection.EnableAutocommit);
		User u = new User();
		u.setUserName(userName);
		u.setPassWord(passWord);
		u = session.selectOne("org.jiira.data.mybatis.dao.IUserDao.getUser", u);
		if (u == null) {
			SALog.warn("装备表没有查询到数据……");
		}
		return u;
	}

	/**
	 * 按照这种模式做
	 */
	@Override
	public User getUser(String userName, String passWord) {
		SqlSession session = DBSessionFactory.getSqlSessionFactory(CommandCollection.DB_ID_BASETABLE)
				.openSession(CommandCollection.EnableAutocommit);
		IUserDao iud = session.getMapper(IUserDao.class);
		User u = iud.getUser(userName, passWord);
		return u;
	}

	public List<User> getUsers() {
		SqlSession session = DBSessionFactory.getSqlSessionFactory(CommandCollection.DB_ID_BASETABLE)
				.openSession(CommandCollection.EnableAutocommit);
		IUserDao iud = session.getMapper(IUserDao.class);
		List<User> u = iud.getUsers();
		return u;
	}
}
