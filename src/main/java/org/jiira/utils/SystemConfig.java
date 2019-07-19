package org.jiira.utils;

import java.util.LinkedHashMap;

import core.utils.sys.SADBConfigType;

public class SystemConfig {
	private int version;// 确定前端是否更新
	private String log4jPath;
	private int port;
	private int threadPool;
	private int heartMinTime;
	private int heartMaxTime;
	private boolean enableDBShard;
	private int baseThreadDelay;
	private int pvpThreadDelay;
	private int pveThreadDelay;
	private int leisurePool;
	private int competitionPool;
	private int roomPool;
	private int cardBoardV;
	private int cardBoardH;
	private int cardBoardTotal;
	private int cardGroupCount;
	private LinkedHashMap<String, SADBConfigType> dbConfig;

	private static SystemConfig _instance;

	public static SystemConfig instance() {
		if (null == _instance) {
			_instance = new SystemConfig();
		}
		return _instance;
	}

	public static void setValue(SystemConfig sys) {
		_instance = sys;
		_instance.cardBoardTotal = _instance.cardBoardV * _instance.cardBoardH;
	}

	public int getVersion() {
		return version;
	}

	public String getLog4jPath() {
		return log4jPath;
	}

	public int getPort() {
		return port;
	}

	public int getThreadPool() {
		return threadPool;
	}

	public boolean isEnableDBShard() {
		return enableDBShard;
	}

	public int getBaseThreadDelay() {
		return baseThreadDelay;
	}

	public int getPvpThreadDelay() {
		return pvpThreadDelay;
	}

	public int getPveThreadDelay() {
		return pveThreadDelay;
	}

	public int getLeisurePool() {
		return leisurePool;
	}

	public int getCompetitionPool() {
		return competitionPool;
	}

	public int getRoomPool() {
		return roomPool;
	}

	public LinkedHashMap<String, SADBConfigType> getDbConfig() {
		return dbConfig;
	}

	public int getCardBoardV() {
		return cardBoardV;
	}

	public int getCardBoardH() {
		return cardBoardH;
	}
	
	public int getCardBoardTotal() {
		return cardBoardTotal;
	}

	public int getCardGroupCount() {
		return cardGroupCount;
	}
	public int getHeartMinTime() {
		return heartMinTime;
	}
	public int getHeartMaxTime() {
		return heartMaxTime;
	}

	public SADBConfigType getSADBConfigType(String key) {
		if (dbConfig.containsKey(key)) {
			return dbConfig.get(key);
		}
		return null;
	}
}
