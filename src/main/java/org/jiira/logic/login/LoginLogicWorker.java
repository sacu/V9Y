package org.jiira.logic.login;


import org.jiira.core.factory.worker.SAMessageLogicWorker;
import org.jiira.data.mybatis.pojo.User;
import org.jiira.data.mybatis.service.IUserService;
import org.jiira.protobuf.ProtobufType.CLogin;
import org.jiira.protobuf.ProtobufType.SUserData;
import org.jiira.utils.ActionCollection;
import org.jiira.utils.CacheCollection;
import org.jiira.utils.CommandCollection;
import org.jiira.utils.CommandCollection.ErrorCodeEnum;
import org.jiira.utils.CommandCollection.ProtoTypeEnum;
import org.jiira.utils.MITSF;

import core.event.SAFactoryEvent;
import core.utils.SALog;
import io.netty.channel.ChannelHandlerContext;

public class LoginLogicWorker extends SAMessageLogicWorker {
	@Override
	protected void init() {
		addEventDispatcherWithHandle(CommandCollection.Sock + ProtoTypeEnum.CLogin, "loginHandler");
	}
	@SuppressWarnings("unused")
	private void loginHandler(SAFactoryEvent e){
		CLogin cLogin = (CLogin)e.getBody();
		SUserData.Builder userData = SUserData.newBuilder();
		IUserService ius = MITSF.getIService(IUserService.class);
		ChannelHandlerContext channel = (ChannelHandlerContext) e.getTarget();
		User u = CacheCollection.getInstance().getUserModelByName(cLogin.getUserName());
		SALog.info(cLogin.getUserName() + " 请求登陆");
		if(null != u){
			if(u.getPassWord().equals(cLogin.getPassWord())){//判断用户名密码
				dispatchEvent(ActionCollection.GetUserData, u, e.getTarget());
			} else {
				errorMessage(channel, ErrorCodeEnum.UserNameOrPassWordError);
			}
		} else {
			errorMessage(channel, ErrorCodeEnum.AccountNotFoundError);
		}
	}
}
