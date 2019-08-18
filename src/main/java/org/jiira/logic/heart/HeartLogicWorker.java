package org.jiira.logic.heart;

import org.jiira.core.factory.worker.SAMessageLogicWorker;
import org.jiira.protobuf.ProtobufType.CHeart;
import org.jiira.utils.CommandCollection;
import org.jiira.utils.CommandCollection.ProtoTypeEnum;

import core.event.SAFactoryEvent;
import core.utils.SALog;
import io.netty.channel.ChannelHandlerContext;

public class HeartLogicWorker extends SAMessageLogicWorker {
	@Override
	protected void init() {
		addEventDispatcherWithHandle(CommandCollection.Sock + ProtoTypeEnum.CHeart, "heartHandler");
	}

	@SuppressWarnings("unused")
	private void heartHandler(SAFactoryEvent e) {
		CHeart heart = (CHeart) e.getBody();
		SALog.info("心跳 : " + heart.getTime());
		
		ChannelHandlerContext channel = (ChannelHandlerContext) e.getTarget();
	}
}
