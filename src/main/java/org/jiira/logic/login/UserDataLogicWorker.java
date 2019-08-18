package org.jiira.logic.login;

import org.jiira.core.factory.worker.SAMessageLogicWorker;
import org.jiira.data.mybatis.pojo.User;
import org.jiira.data.netty.tcp.SocketQueue;
import org.jiira.protobuf.ProtobufType.SUserData;
import org.jiira.utils.ActionCollection;
import org.jiira.utils.CommandCollection.AccountTypeEnum;
import org.jiira.utils.CommandCollection.ErrorCodeEnum;
import org.jiira.utils.CommandCollection.ProtoTypeEnum;

import core.event.SAFactoryEvent;
import core.utils.SALog;
import io.netty.channel.ChannelHandlerContext;

public class UserDataLogicWorker extends SAMessageLogicWorker {
	@Override
	protected void init() {
		addEventDispatcherWithHandle(ActionCollection.GetUserData, "getUserDataHandler");
	}

	@SuppressWarnings("unused")
	private void getUserDataHandler(SAFactoryEvent e) {
		if (e.getType() == ActionCollection.GetUserData) {
			User u = (User) e.getBody();
			ChannelHandlerContext channel = (ChannelHandlerContext) e.getTarget();
			loginValidator(u, channel);
		}
	}
	private void loginValidator(User u, ChannelHandlerContext channel) {
		switch (u.getState()) {
			case Offline: // 不在线
				u.setState(AccountTypeEnum.Online);//改变登录状态
			case Online: {// 已在线
				if(null == SocketQueue.getInstance().get(u.getId())) {//判断卡线
					SocketQueue.getInstance().put(u.getId(), channel);
					SUserData.Builder userData = u.getSUserData();
					userData.setState(u.getState().ordinal());
					sendMessage(channel, ProtoTypeEnum.SUserData, userData.build().toByteArray());//登录成功返回用户数据
					SALog.info("状态 : " + u.getState() + " | 名称 : " + u.getUserName() + " | ID : " + u.getId());
				} else {
					errorMessage(channel, ErrorCodeEnum.RepeatLoginError);
				}
				break;
			}
			case Error: // 登录错误
			default:
				errorMessage(channel, ErrorCodeEnum.AccountError);
				break;
		}
	}
}
