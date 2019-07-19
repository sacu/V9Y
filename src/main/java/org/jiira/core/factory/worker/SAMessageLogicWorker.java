package org.jiira.core.factory.worker;

import org.jiira.data.netty.tcp.SocketQueue;
import org.jiira.data.netty.tcp.SocketQueueElement;
import org.jiira.logic.update.SingleUpdateENV;
import org.jiira.protobuf.ProtobufType.SError;
import org.jiira.utils.ActionCollection;
import org.jiira.utils.CommandCollection;
import org.jiira.utils.CommandCollection.ErrorCodeEnum;
import org.jiira.utils.CommandCollection.ProtoTypeEnum;
import org.jiira.utils.CommandCollection.SingleUpdateTypeEnum;
import org.jiira.utils.SASocketMessage;

import core.factory.SAFactory;
import core.factory.worker.SALogicWorker;
import core.utils.SALog;
import core.utils.sys.SAIOCManager;
import io.netty.channel.ChannelHandlerContext;

public class SAMessageLogicWorker extends SALogicWorker{
	private SAFactory su;
	/**
	 * 推送消息到指定用户
	 * @param userId
	 * @param type
	 * @param cust
	 */
	public void sendMessage(Integer userId, ProtoTypeEnum type, byte[] cust) {
		sendMessage(SocketQueue.getInstance().get(userId).getChannel(), type, cust);
	}

	public void sendMessage(ChannelHandlerContext channel, ProtoTypeEnum type, byte[] cust) {
		SALog.info("发送消息 : " + type.name());
		channel.writeAndFlush(new SASocketMessage(type, cust.length + CommandCollection.SOCK_TYPE_LENGTH, cust));
		channel.flush();
	}
	
	/**
	 * 推送错误消息到指定用户
	 * @param channel
	 * @param errorCode
	 */
	public void errorMessage(ChannelHandlerContext channel, ErrorCodeEnum errorCode){
		//测试
		SocketQueueElement sqe = SocketQueue.getInstance().get(channel);
		if(null != sqe){
			SALog.info("用户 : " + sqe.getUserID() + ", Error code : " + errorCode.name());
		} else {
			SALog.info("用户 : null(可能是登陆截), Error code : " + errorCode.name());
		}
		//
		SError.Builder error = SError.newBuilder();
		error.setCode(errorCode.ordinal());
		sendMessage(channel, ProtoTypeEnum.SError, error.build().toByteArray());
	}
	public void SendSingleUpdateMessage(Integer changeId, Integer acceptId, SingleUpdateTypeEnum type, int id, int count){
		SendSingleUpdateMessage(changeId, acceptId, new SingleUpdateTypeEnum[]{type}, new int[]{id}, new int[]{count});
	}
	public void SendSingleUpdateMessage(Integer changeId, Integer acceptId, SingleUpdateTypeEnum[] types, int[] ids, int[] counts){
		if(null == su){
			su = SAIOCManager.getInstance().getFactory("com.game.sa.factory.UpdateFactory");
		}
		su.dispatchEvent(ActionCollection.SingleUpdate, SingleUpdateENV.getSingle(changeId, acceptId, types, ids, counts));
	}
}
