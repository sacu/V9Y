package org.jiira.logic.test;

import org.jiira.core.factory.worker.SAMessageLogicWorker;
import org.jiira.protobuf.ProtobufType.CTest;
import org.jiira.utils.CommandCollection;
import org.jiira.utils.CommandCollection.ErrorCodeEnum;
import org.jiira.utils.CommandCollection.ProtoTypeEnum;

import core.event.SAFactoryEvent;
import core.utils.SALog;
import io.netty.channel.ChannelHandlerContext;

public class TestLogicWorker extends SAMessageLogicWorker {
	@Override
	protected void init() {
		addEventDispatcherWithHandle(CommandCollection.Sock + ProtoTypeEnum.CTest, "testHandler");
	}
	@SuppressWarnings("unused")
	private void testHandler(SAFactoryEvent e){
		CTest cLogin = (CTest)e.getBody();
		ChannelHandlerContext channel = (ChannelHandlerContext) e.getTarget();
		SALog.info("收到消息");
		//返回消息
		if(true) {
			//sendMessage(channel, ProtoTypeEnum.SUserData, userData.build().toByteArray());//登录成功返回用户数据
		} else {
			errorMessage(channel, ErrorCodeEnum.AccountNotFoundError);
		}
	}
}
