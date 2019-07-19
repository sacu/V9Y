package org.jiira.logic.update;

import org.jiira.core.factory.worker.SAMessageLogicWorker;
import org.jiira.data.netty.tcp.SocketQueue;
import org.jiira.data.netty.tcp.SocketQueueElement;
import org.jiira.protobuf.ProtobufType.SSingleUpdate;
import org.jiira.utils.ActionCollection;
import org.jiira.utils.CommandCollection.ErrorCodeEnum;
import org.jiira.utils.CommandCollection.ProtoTypeEnum;

import core.event.SAFactoryEvent;
import io.netty.channel.ChannelHandlerContext;

public class SingleUpdateLogicWorker extends SAMessageLogicWorker{
	protected void init() {
		addEventDispatcherWithHandle(ActionCollection.SingleUpdate, "singleUpdateHandler");
	}
	
	@SuppressWarnings("unused")
	private void singleUpdateHandler(SAFactoryEvent e){
		SingleUpdateENV env = (SingleUpdateENV)e.getBody();
		SocketQueueElement sqe = SocketQueue.getInstance().get(env.acceptId);
		if(null != sqe){
			SSingleUpdate.Builder singleUpdate = SSingleUpdate.newBuilder();
			singleUpdate.setUserID(env.changeId);//消费者ID（需要改变的人的ID）
			int len = env.ids.length;
			for(int i = 0; i < len; ++i){
				singleUpdate.addIDs(env.ids[i]);
				singleUpdate.addCounts(env.counts[i]);
				singleUpdate.addKeys(env.types[i].ordinal());
			}
			sendMessage(sqe.getChannel(), ProtoTypeEnum.SSingleUpdate, singleUpdate.build().toByteArray());
		} else {
			errorMessage((ChannelHandlerContext)e.getTarget(), ErrorCodeEnum.AccountOfflineError);// 不在线
		}
	}
}
