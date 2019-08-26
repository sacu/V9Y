package org.jiira.data.mybatis.pojo;

import org.jiira.protobuf.ProtobufType.SUserData;
import org.jiira.utils.CommandCollection.AccountTypeEnum;

public class User {
	private int id;
	private String userName;
	private String passWord;
	private String nickName;
	private int coin;
	private int diamond;
	private String appid;
	private String appsecret;
	private AccountTypeEnum state;//状态

	private SUserData.Builder userData;
	//各种类型
	private String roomName;
	public User(){
		userData = SUserData.newBuilder();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public int getCoin() {
		return coin;
	}
	public void setCoin(int coin) {
		this.coin = coin;
	}
	public int getDiamond() {
		return diamond;
	}
	public void setDiamond(int diamond) {
		this.diamond = diamond;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getAppsecret() {
		return appsecret;
	}
	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}
	public AccountTypeEnum getState() {
		return state;
	}
	public void setState(AccountTypeEnum state) {
		this.state = state;
	}
	public SUserData.Builder getSUserData(){
		userData.clear();
		userData.setId(id);
		userData.setUserName(userName);
		userData.setNickName(nickName);
		userData.setCoin(coin);
		userData.setDiamond(diamond);
		userData.setState(state.ordinal());
		return userData;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
}
