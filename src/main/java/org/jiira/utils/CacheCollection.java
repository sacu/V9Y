package org.jiira.utils;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.jiira.data.mybatis.pojo.User;
import org.jiira.data.mybatis.service.IUserService;
import org.jiira.logic.fish.k.FishRoom;
import org.jiira.utils.CommandCollection.AccountTypeEnum;
import org.jiira.utils.CommandCollection.GameTypeEnum;

public class CacheCollection {
	// 用户
	private Map<Integer, User> usersByID;// id : key 信息获取
	private Map<String, User> usersByName;// account : key 登录
	private Map<String, User> usersByNickName;// account : key 登录
	/**
	 * 房间(池) cfishRoom,当一个房间人数达到上限后！房间对象会进入这个数组,同时从ofishRoom中移除
	 * ofishRoom,当新建房间或有人退出房间时,房间对象会进入这个数组,同时从cfishRoom中移除
	 * 当ofishRoom中不存在房间时,通过判断,如果未达到最大房间数,则可新建房间并加入ofishRoom
	 */
	private int fishRoomCount1;
	private int fishRoomCount2;
	private int fishRoomCount3;
	//简单
	private Vector<FishRoom> cfishRoom1;// 已满员的房间池
	private Vector<FishRoom> ofishRoom1;// 开放进入的房间池
	//普通
	private Vector<FishRoom> cfishRoom2;// 已满员的房间池
	private Vector<FishRoom> ofishRoom2;// 开放进入的房间池
	//高级
	private Vector<FishRoom> cfishRoom3;// 已满员的房间池
	private Vector<FishRoom> ofishRoom3;// 开放进入的房间池
	
	private Map<String, FishRoom> fishRoomByName;//name = enum + id
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
		// 房间1
		fishRoomCount1 = 0;
		cfishRoom1 = new Vector<>();
		ofishRoom1 = new Vector<>();
		// 房间2
		fishRoomCount2 = 0;
		cfishRoom2 = new Vector<>();
		ofishRoom2 = new Vector<>();
		// 房间3
		fishRoomCount3 = 0;
		cfishRoom3 = new Vector<>();
		ofishRoom3 = new Vector<>();
		
		fishRoomByName = new ConcurrentHashMap<>();
	}

	/// ###############################################################
	/// 用户表
	/// ###############################################################
	/**
	 * 通过userName（帐号）获取用户信息
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

	public User getUserModelByNickName(String nickName) {
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
	/// ###############################################################
	/// 房间列表
	/// ###############################################################
	public FishRoom getFishRoom(GameTypeEnum type) {
		FishRoom fr = null;
		Vector<FishRoom> ofishRoom;
		int fishRoomCount;
		switch(type) {
			case FishOrdinary:{
				ofishRoom = ofishRoom2;
				fishRoomCount = fishRoomCount2;
				break;
			}
			case FishDifficultie:{
				ofishRoom = ofishRoom3;
				fishRoomCount = fishRoomCount3;
				break;
			}
			case FishSimple:
			case None:
			default:
				ofishRoom = ofishRoom1;
				fishRoomCount = fishRoomCount1;
		}
		
		if(ofishRoom.size() > 0) {
			fr = ofishRoom.get(0);
		} else if(fishRoomCount < CommandCollection.FISH_ROOM_MAX) {
			fr = new FishRoom(fishRoomCount, type);
			fishRoomByName.put(fr.getRoomName(), fr);//枚举+ID 组成房间名
			ofishRoom.add(fr);
			switch(type) {
				case FishOrdinary:{
					++fishRoomCount2;
					break;
				}
				case FishDifficultie:{
					++fishRoomCount3;
					break;
				}
				case FishSimple:
				case None:
				default:
					++fishRoomCount1;
			}
			
		}
		return fr;
	}
	//枚举+名字组成的房间名
	public FishRoom getFishRoom(String roomName) {
		if(fishRoomByName.containsKey(roomName)) {
			return fishRoomByName.get(roomName);
		}
		return null;
	}
	public void outFishRoom(User user) {
		if(null != user.getRoomName()) {
			FishRoom fr = getFishRoom(user.getRoomName());
			fr.out(user.getId());
		}
	}
	
	public void switchFishRoom(FishRoom fr) {
		Vector<FishRoom> ofishRoom;
		Vector<FishRoom> cfishRoom;
		switch(fr.getType()) {
			case FishOrdinary:{
				ofishRoom = ofishRoom2;
				cfishRoom = cfishRoom2;
				break;
			}
			case FishDifficultie:{
				ofishRoom = ofishRoom3;
				cfishRoom = cfishRoom3;
				break;
			}
			case FishSimple:
			case None:
			default:
				ofishRoom = ofishRoom1;
				cfishRoom = cfishRoom1;
		}
		
		if(fr.getCount() < CommandCollection.FISH_ROOM_USER_MAX) {//到开放房间
			if(cfishRoom.indexOf(fr) != -1) {//从关闭房间移除
				cfishRoom.remove(fr);
			}
			if(ofishRoom.indexOf(fr) == -1) {//加入开放房间
				ofishRoom.add(fr);
			}
		} else {//到关闭房间
			if(cfishRoom.indexOf(fr) == -1) {//加入关闭房间
				cfishRoom.add(fr);
			}
			if(ofishRoom.indexOf(fr) != -1) {//从开放房间移除
				ofishRoom.remove(fr);
			}
		}
	}
}
