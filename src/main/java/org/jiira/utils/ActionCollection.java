package org.jiira.utils;

import java.util.Random;

import org.jiira.utils.CommandCollection.BigTypeEnum;
import org.jiira.utils.CommandCollection.SingleUpdateTypeEnum;

public class ActionCollection {
	//杂表标识
	public final static Integer Other_RoundTime = 1;
	public final static Integer Other_Hypothecate0 = 2;
	public final static Integer Other_Hypothecate20 = 3;
	public final static Integer Other_Hypothecate50 = 4;
	public final static Integer Other_Hypothecate200 = 5;
	public final static Integer Other_DailyMax = 6;
	public final static Integer Other_DailyGetMax = 7;
	public final static Integer Other_DefaultPopoSkin = 8;
	public final static Integer Other_DefaultDialogueSkin = 9;
	public final static Integer Other_DefaultCardSkin = 10;
	public final static Integer Other_DefaultModelSkin = 11;
	public final static Integer Other_RefreshDaily = 12;
	public final static Integer Other_EnterDelayTime = 13;
	public final static Integer Other_FunDay = 14;
	public final static Integer Other_BuyFundDiamond = 15;
	public final static Integer Other_FundDiamondReward = 16;
	public final static Integer Other_BuyFundCurrency = 17;
	public final static Integer Other_FundCurrencyReward = 18;
	public final static Integer Other_DefaultHeadID = 19;
	
	public final static int MillisToDay = 86400000;
	public final static int SecondToDay = MillisToDay / 1000;
	public final static int SixHourToSecond = 21600;//6小时

	public final static int MaxMonth = 31;
	public final static int MaxLife = 100;
	public final static boolean PASS = true;
	public final static Integer InvalidUserID = -1;
	public final static Integer InvalidCardID = 0;
	public final static int InvalidValue = 0;
	public final static int InvalidIndex = -1;
	public final static int ValueOne = 1;
	public final static int CardTotal = 5;
	public final static String GetUserData = ".GetUserData";
	public final static String SingleUpdate = ".single.update";
	public final static int LaddyCombo = 3;
	public final static int Angle = 4;// 角度
	public final static int LevelGift = 32;
	public final static int ListLevel = 32;
	public final static String GameLandRandomRound = ".gameland.random.round";// 随机一个回合
	public final static String GameLandCurrentFail = ".gameland.current.fail";// 当前用户失败
	public final static String GameLandCloseRoom = ".gameland.close.room";// 关闭当前房间

	public final static int Battle_Begin = 0;
	public final static int Battle_Ready = 1;
	public final static int Battle_Process = 2;
	public final static int Battle_End = 3;
	public final static int Battle_Change = 4;
	public final static int[] CurrencyID = new int[] { CommandCollection.Currency };
	public final static int[] DiamondID = new int[] { CommandCollection.Diamond };
	public final static int[] CountOne = new int[] { 1 };

	private static Random random = new Random();// 随机种子

	public static Random getRandom() {
		return random;
	}

	public static long getTime() {
		return System.currentTimeMillis();
	}
	
	public static SingleUpdateTypeEnum getSingleUpdateTypeEnum(BigTypeEnum type) {
		switch (type) {
		case Exp:
			return SingleUpdateTypeEnum.Exp;
		case Currency:
			return SingleUpdateTypeEnum.Currency;
		case Diamond:
			return SingleUpdateTypeEnum.Diamond;
		case Card:
		case Pack:
		case Skin:
		case Equip:
			return SingleUpdateTypeEnum.Item;
		default:
			return SingleUpdateTypeEnum.None;
		}
	}
}
