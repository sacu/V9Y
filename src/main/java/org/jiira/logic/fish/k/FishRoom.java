package org.jiira.logic.fish.k;

import java.util.ArrayList;

import org.jiira.protobuf.SAProtoDecode.STChapter;
import org.jiira.utils.ActionCollection;
import org.jiira.utils.CacheCollection;
import org.jiira.utils.CommandCollection;
import org.jiira.utils.CommandCollection.GameTypeEnum;

import core.utils.SALog;

public class FishRoom {
	private int count;//人数
	private GameTypeEnum type;
	private String roomName;
	private int roomID;//room id
	private long startTime;//当前关卡开始时间
	private int chapterSN;//当前关卡序号(因为在内存中关卡是个数组)
	private ArrayList<Integer> fishList;//存活的鱼群序号
	private int[] userList;//用户ID列表
	private ArrayList<STChapter> chapterList;
	private STChapter chapter;
	private ArrayList<Integer> hangUp;//挂起状态
	public FishRoom(int roomID, GameTypeEnum type) {
		this.roomID = roomID;
		this.type = type;
		roomName = type.toString() + roomID;
		chapterList = STChapter.getList();
		fishList = new ArrayList<>();
		hangUp = new ArrayList<>();
		clear();
		nextChapter();
	}
	public GameTypeEnum getType() {
		return type;
	}
	public String getRoomName() {
		return roomName;
	}
	public int getRoomID() {
		return roomID;
	}
	public int getCount() {
		return count;
	}
	public ArrayList<Integer> getFishList(){
		return fishList;
	}
	public int[] getUserList() {
		return userList;
	}
	public long getStartTime() {
		return startTime;
	}
	public int getChapterID() {
		return chapter.getId();
	}
	
	public boolean hangUpAndReset(int userID) {
		boolean isExist = false;
		for(int i = 0; i < CommandCollection.FISH_ROOM_USER_MAX; ++i) {
			if(userList[i] == userID) {
				isExist = true;
				break;
			}
		}
		if(isExist) {
			hangUp.add(userID);
			isExist = hangUp.size() >= Math.floor(count / 2);
			if(isExist) {
				nextChapter();
			}
		}
		//超过半数则下一关
		return isExist;
	}
	
	public boolean join(int userID) {
		if(count >= CommandCollection.FISH_ROOM_USER_MAX) {
			return false;
		}
		for(int i = 0; i < CommandCollection.FISH_ROOM_USER_MAX; ++i) {
			if(userList[i] == -1) {
				userList[i] = userID;
				++count;
				break;
			}
		}
		if(count == CommandCollection.FISH_ROOM_USER_MAX) {//切换到关闭房间列表
			CacheCollection.getInstance().switchFishRoom(this);
		}
		return true;
	}
	public boolean out(int userID) {
		for(int i = 0; i < CommandCollection.FISH_ROOM_USER_MAX; ++i) {
			if(userList[i] == userID) {
				userList[i] = -1;
				--count;
				if(count == CommandCollection.FISH_ROOM_USER_MAX - 1) {//如果退出之前是满员,则切换到打开房间列表 max - 1 = 3
					CacheCollection.getInstance().switchFishRoom(this);
				} else if(count == 0) {
					clear();
				}
				return true;
			}
		}
		return false;
	}
	
	public void nextChapter() {
		chapter = chapterList.get(chapterSN);
		int ns_len = chapter.getSOF().length;
		fishList.clear();
		hangUp.clear();
		for(int i = 0; i < ns_len; ++i) {
			fishList.add(i);
		}
		startTime = ActionCollection.getTime();
		if(++chapterSN >= chapterList.size()) {
			chapterSN = 0;
		}
	}
	/**
	 * 清空房间
	 */
	public void clear() {
		count = 0;
		chapterSN = 0;
		userList = new int[CommandCollection.FISH_ROOM_USER_MAX];
		for(int i = 0; i < CommandCollection.FISH_ROOM_USER_MAX; ++i) {
			userList[i] = -1;
		}
		//进行一次切换
		CacheCollection.getInstance().switchFishRoom(this);
		SALog.info("清空房间：" + roomName);
	}
}
