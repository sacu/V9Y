package org.jiira.logic.login;


import org.jiira.core.factory.worker.SAMessageLogicWorker;
import org.jiira.protobuf.ProtobufType.CLogin;
import org.jiira.protobuf.ProtobufType.SUserData;
import org.jiira.utils.CommandCollection;
import org.jiira.utils.CommandCollection.ProtoTypeEnum;

import core.event.SAFactoryEvent;
import io.netty.channel.ChannelHandlerContext;

public class LoginLogicWorker extends SAMessageLogicWorker {
	@Override
	protected void init() {
		addEventDispatcherWithHandle(CommandCollection.Sock + ProtoTypeEnum.CLogin, "loginHandler");
	}
	@SuppressWarnings("unused")
	private void loginHandler(SAFactoryEvent e){
		CLogin cLogin = (CLogin)e.getBody();
		System.out.println("userName : " + cLogin.getUserName());
		System.out.println("passWord : " + cLogin.getPassWord());
		SUserData.Builder userData = SUserData.newBuilder();
		userData.setCode(123);
		ChannelHandlerContext channel = (ChannelHandlerContext) e.getTarget();
		sendMessage(channel, ProtoTypeEnum.SUserData, userData.build().toByteArray());//登录成功返回用户数据
	}
}
