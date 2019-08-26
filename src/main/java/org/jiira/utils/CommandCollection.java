package org.jiira.utils;
import org.jiira.protobuf.ProtobufType.*;
import com.google.protobuf.InvalidProtocolBufferException;
public class CommandCollection {
	public static enum ProtoTypeEnum {
		CLogin, //
		SUserData, //
		CEnterRoom, //
		COutRoom, //
		SOutRoom, //
		SFishChapter, //
		CHangUpRoom, //
		SError, //
		SSingleUpdate, //
		CTest, //
		STest, //
		CHeart, //
	}
	public static enum GameTypeEnum {
		None, //无
		FishSimple, //初级捕鱼
		FishOrdinary, //中级捕鱼
		FishDifficultie, //高级捕鱼
	}
	public static enum RankTypeEnum {
		Leisure, //
		Competition, //
		Gay, //
	}
	public static enum ChatTypeEnum {
		World, //
		Organization, //
		Single, //
	}
	public static enum AccountTypeEnum {
		Offline, //
		Online, //
		Error, //
	}
	public static enum TeamTypeEnum {
		None, //
		Red, //
		Blue, //
	}
	public static enum BigTypeEnum {
		None, //没有
		Exp, //经验
		Currency, //游戏币
		Diamond, //钻石
		Card, //卡牌类
		Pack, //奖励包
		Skin, //皮肤类
		Equip, //装备类
	}
	public static enum SmallTypeEnum {
		None, //没有
		Exp, //经验
		Currency, //游戏币
		Diamond, //钻石
		Card, //普通卡牌
		ADVCard, //技能卡牌
		BlinkCard, //普通闪卡
		ADVBlinkCard, //技能闪卡
		FreePack, //免费奖励包
		PayPack, //付费奖励包
		PopoSkin, //聊天气泡皮肤
		DialogueSkin, //对白气泡皮肤
		CardSkin, //卡牌皮肤
		ModelSkin, //角色形象皮肤
		SkillEquip, //技能装备
		AttributeEquip, //属性装备
	}
	public static enum ErrorCodeEnum {
		SocketDisconnectError, //与服务器断开连接
		UserNameOrPassWordError, //用户名或密码错误
		AccountNotFoundError, //用户不存在
		NickNameExistError, //昵称已存在
		AccountIsFoundError, //用户已存在
		AccountCreateError, //用户创建失败
		AccountError, //帐号异常
		RepeatLoginError, //帐号已在登录状态
		AccountOfflineError, //目标用户是离线状态
		BalanceIsNotEnoughError, //余额不足
		DataBaseError, //数据库操作失败
		NoFoundFishRoomError, //房间不存在
		NoJoinFishRoomError, //没有加入这个房间
		FirendListError, //获取好友列表失败
		FirendInfoError, //获取好友信息失败
		AddFirendError, //添加好友失败
		AddFirendToMysqlError, //添加好友更新数据库失败
		CurrencyToMysqlError, //游戏币更新数据库失败
		DiamondToMysqlError, //钻石更新数据库失败
		LackOfCurrencyError, //游戏币不足
		LackOfDiamondError, //钻石不足
		FightError, //战斗异常
		FightReadyError, //战斗准备阶段异常
		FightRoomCloseError, //战斗房间已关闭
		FightStepError, //战斗步骤异常（出牌位置或不存在该手牌）
		FightStepNoTeamError, //错误的出牌队伍
		BuySkinError, //皮肤购买失败
		BuyGiftError, //礼包购买失败
		SellSkinError, //皮肤出售失败
		SellGiftError, //礼包出售失败
		MailRemoveError, //邮件删除失败
		MailSendError, //邮件发送失败
		NotFoundCardListError, //没有找到卡牌列表
		NotFoundCardGroupError, //没有找到默认卡组
		CardGroupUseError, //卡组装载失败
		CardGroupUnUseError, //卡组卸载失败
		CardGroupEnditError, //卡组编辑失败
		CardNotFoundError, //卡牌不存在
		CardEquipUseError, //卡牌装备装载失败
		CardEquipUnUseError, //卡牌装备卸载失败
		NotFoundItemError, //物品不存在
		UpdateSkinError, //皮肤更新失败
		ItemListError, //物品列表获取失败
		UpdateHeadError, //更新头像失败
		ToDaySignInError, //今天已经签到过了
		MonthSignInError, //本月签到已结束
		SignInError, //签到失败
		LevelGiftInfoError, //等级礼包信息错误
		GiftNotFoundError, //礼包不存在
		UseGiftError, //使用礼包错误
		UseGiftMysqlError, //使用礼包错误[Mysql]
		GiftInfoError, //礼包信息错误
		GiftOpenError, //打开礼包错误
		ToDayReceiveError, //今天已经领取过了
		NoRepeatPurchaseError, //不能重复购买
		FundOverdueError, //基金已过期
		RefreshMissionError, //没有可刷新的任务
		MissionRefreshTimeToMysqlError, //任务冷却时间更新数据库失败
		GuideStepRefreshError, //新手引导阶段更新失败
		HookError, //操你妈用外挂
	}
	public static enum SingleUpdateTypeEnum {
		None, //无
		Exp, //经验
		Level, //等级
		Currency, //游戏币
		Diamond, //钻石
		win, //胜
		fail, //负
		dogfall, //平
		udSkin, //穿戴的皮肤
		Item, //物品
		LevelGiftState, //等级礼包
		FundDiamond, //钻石基金日期更新
		EndFundDiamondDay, //钻石基金最后领取天数更新
		FundCurrency, //游戏币基金日期更新
		EndFundCurrencyDay, //游戏币基金最后领取天数更新
		SignIn, //签到更新
		SignInCount, //签到次数更新
	}
	public static enum BattleMessageTypeEnum {
		Greet, //问候
		Amazing, //惊叹
		Happy, //开心
		Sad, //悲伤
		Start, //开场白
	}
	public static enum MissionTypeEnum {
		Money, //货币数量
		CardCount, //卡牌数量
		WinCount, //获胜次数
		BattleCount, //比赛次数
		CardUseCount, //放置卡牌次数
		CardFlipCount, //翻转卡牌次数
		Consume, //商城消费
		PopCount, //发气泡次数
		FailCount, //失败次数
		WinContry, //胜利国家限制
	}
	public static enum MissionConditionTypeEnum {
		None, //无
		All, //全部
		Money, //任意货币
		Currency, //游戏币
		Diamond, //钻石
		AllCard, //任意卡牌
		Card, //普通卡牌
		BlinkCard, //闪卡
		AllBattle, //任意比赛
		AllPVPBatle, //任意PVP比赛
		CompetitionPVPBatle, //竞技PVP比赛
		LeisurePVPBatle, //休闲PVP比赛
		AllPVEBatle, //任意PVE比赛
		TutorialPVEBatle, //教学PVE比赛
		TryPVEBatle, //试玩PVE比赛
		OrdinaryPVEBatle, //普通PVE比赛
	}
	public static enum MissionNameTypeEnum {
		Daily, //每日
		Achievement, //成就
	}
	public final static String Sock = ".sock";
	public final static String DB_ID_BASETABLE = "gamedb";
	public final static boolean EnableAutocommit = true;
	public final static int FISH_ROOM_MAX = 1000;
	public final static int FISH_ROOM_USER_MAX = 4;
	public final static int SOCK_TYPE_LENGTH = 2;
	public final static int SOCK_CONTEXT_LENGTH = 2;
	public final static int SOCK_HEAD_LENGTH = 4;
	public final static int UPLEVEL = 1000;
	public final static int Exp = 33;
	public final static int Currency = 66;
	public final static int Diamond = 88;
	public static Object getDataModel(ProtoTypeEnum type, byte[] bytes){
		try {
			switch(type){
				case CLogin:{
					return CLogin.parseFrom(bytes);
				}
				case SUserData:{
					return SUserData.parseFrom(bytes);
				}
				case CEnterRoom:{
					return CEnterRoom.parseFrom(bytes);
				}
				case COutRoom:{
					return COutRoom.parseFrom(bytes);
				}
				case SOutRoom:{
					return SOutRoom.parseFrom(bytes);
				}
				case SFishChapter:{
					return SFishChapter.parseFrom(bytes);
				}
				case CHangUpRoom:{
					return CHangUpRoom.parseFrom(bytes);
				}
				case SError:{
					return SError.parseFrom(bytes);
				}
				case SSingleUpdate:{
					return SSingleUpdate.parseFrom(bytes);
				}
				case CTest:{
					return CTest.parseFrom(bytes);
				}
				case STest:{
					return STest.parseFrom(bytes);
				}
				case CHeart:{
					return CHeart.parseFrom(bytes);
				}
			}
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static BigTypeEnum getBigTypeEnum(int itemId) {
		if (itemId >= 0 && itemId <= 0) {
			return BigTypeEnum.None;
		} else if (itemId >= 33 && itemId <= 33) {
			return BigTypeEnum.Exp;
		} else if (itemId >= 66 && itemId <= 66) {
			return BigTypeEnum.Currency;
		} else if (itemId >= 88 && itemId <= 88) {
			return BigTypeEnum.Diamond;
		} else if (itemId >= 1000 && itemId <= 4999) {
			return BigTypeEnum.Card;
		} else if (itemId >= 5000 && itemId <= 5199) {
			return BigTypeEnum.Pack;
		} else if (itemId >= 6000 && itemId <= 6399) {
			return BigTypeEnum.Skin;
		} else if (itemId >= 7000 && itemId <= 9999) {
			return BigTypeEnum.Equip;
		} else 
			return BigTypeEnum.None;
	}
	public static SmallTypeEnum getSmallTypeEnum(int itemId) {
		if (itemId >= 0 && itemId <= 0) {
			return SmallTypeEnum.None;
		} else if (itemId >= 33 && itemId <= 33) {
			return SmallTypeEnum.Exp;
		} else if (itemId >= 66 && itemId <= 66) {
			return SmallTypeEnum.Currency;
		} else if (itemId >= 88 && itemId <= 88) {
			return SmallTypeEnum.Diamond;
		} else if (itemId >= 1000 && itemId <= 1999) {
			return SmallTypeEnum.Card;
		} else if (itemId >= 2000 && itemId <= 2999) {
			return SmallTypeEnum.ADVCard;
		} else if (itemId >= 3000 && itemId <= 3999) {
			return SmallTypeEnum.BlinkCard;
		} else if (itemId >= 4000 && itemId <= 4999) {
			return SmallTypeEnum.ADVBlinkCard;
		} else if (itemId >= 5000 && itemId <= 5099) {
			return SmallTypeEnum.FreePack;
		} else if (itemId >= 5100 && itemId <= 5199) {
			return SmallTypeEnum.PayPack;
		} else if (itemId >= 6000 && itemId <= 6099) {
			return SmallTypeEnum.PopoSkin;
		} else if (itemId >= 6100 && itemId <= 6199) {
			return SmallTypeEnum.DialogueSkin;
		} else if (itemId >= 6200 && itemId <= 6299) {
			return SmallTypeEnum.CardSkin;
		} else if (itemId >= 6300 && itemId <= 6399) {
			return SmallTypeEnum.ModelSkin;
		} else if (itemId >= 7000 && itemId <= 9499) {
			return SmallTypeEnum.SkillEquip;
		} else if (itemId >= 9500 && itemId <= 9999) {
			return SmallTypeEnum.AttributeEquip;
		} else 
			return SmallTypeEnum.None;
	}
}