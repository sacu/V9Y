package org.jiira.logic.fish;


import java.util.ArrayList;

import org.jiira.core.factory.worker.SAMessageLogicWorker;
import org.jiira.data.mybatis.pojo.User;
import org.jiira.data.netty.tcp.SocketQueue;
import org.jiira.data.netty.tcp.SocketQueueElement;
import org.jiira.logic.fish.k.FishRoom;
import org.jiira.protobuf.ProtobufType.SFishChapter;
import org.jiira.protobuf.ProtobufType.SOutRoom;
import org.jiira.utils.ActionCollection;
import org.jiira.utils.CacheCollection;
import org.jiira.utils.CommandCollection;
import org.jiira.utils.CommandCollection.ErrorCodeEnum;
import org.jiira.utils.CommandCollection.GameTypeEnum;
import org.jiira.utils.CommandCollection.ProtoTypeEnum;

import core.event.SAFactoryEvent;
import io.netty.channel.ChannelHandlerContext;

public class FishLogicWorker extends SAMessageLogicWorker {
	@Override
	protected void init() {
		addEventDispatcherWithHandle(CommandCollection.Sock + ProtoTypeEnum.CEnterRoom, "enterRoomHandler");
		addEventDispatcherWithHandle(CommandCollection.Sock + ProtoTypeEnum.COutRoom, "outRoomHandler");
		addEventDispatcherWithHandle(CommandCollection.Sock + ProtoTypeEnum.CHangUpRoom, "hangUpRoomHandler");
	}
	@SuppressWarnings("unused")
	private void enterRoomHandler(SAFactoryEvent e){
		//getFishRoom 随后需要根据炮台等级换算进入的房间等级,这里暂时写死,随后需要修改
		FishRoom fr = CacheCollection.getInstance().getFishRoom(GameTypeEnum.FishSimple);
		ChannelHandlerContext channel = (ChannelHandlerContext) e.getTarget();
		SocketQueueElement sqe = SocketQueue.getInstance().get(channel);
		int userID = sqe.getUserID();
		fr.join(userID);
		//设置用户进入的房间
		User user = CacheCollection.getInstance().getUserModelByID(userID);
		if(null != user.getRoomName()) {//如果之前在其他房间,则强制退出
			FishRoom otherFr = CacheCollection.getInstance().getFishRoom(user.getRoomName());
			otherFr.out(userID);
		}
		//向客户端返回房间信息
		user.setRoomName(fr.getRoomName());
		SFishChapter.Builder fishChapter = getFishChapter(fr);
		sendMessage(channel, ProtoTypeEnum.SFishChapter, fishChapter.build().toByteArray());
	}
	@SuppressWarnings("unused")
	private void outRoomHandler(SAFactoryEvent e){
		ChannelHandlerContext channel = (ChannelHandlerContext) e.getTarget();
		SocketQueueElement sqe = SocketQueue.getInstance().get(channel);
		int userID = sqe.getUserID();
		User user = CacheCollection.getInstance().getUserModelByID(userID);
		if(null != user.getRoomName()) {
			FishRoom fr = CacheCollection.getInstance().getFishRoom(user.getRoomName());
			if(null != fr) {
				fr.out(userID);
				//退出房间成功
				SOutRoom.Builder outRoom = SOutRoom.newBuilder();
				sendMessage(channel, ProtoTypeEnum.SOutRoom, outRoom.build().toByteArray());
			} else {
				errorMessage(channel, ErrorCodeEnum.NoFoundFishRoomError);
			}
		} else {
			user.setRoomName(null);
			//抛出异常
			errorMessage(channel, ErrorCodeEnum.NoJoinFishRoomError);
		}
	}
	@SuppressWarnings("unused")
	private void hangUpRoomHandler(SAFactoryEvent e){
		ChannelHandlerContext channel = (ChannelHandlerContext) e.getTarget();
		SocketQueueElement sqe = SocketQueue.getInstance().get(channel);
		int userID = sqe.getUserID();
		User user = CacheCollection.getInstance().getUserModelByID(userID);
		if(null != user.getRoomName()) {
			FishRoom fr = CacheCollection.getInstance().getFishRoom(user.getRoomName());
			if(fr.hangUpAndReset(userID)) {
				SFishChapter.Builder fishChapter = getFishChapter(fr);
				int[] userList = fr.getUserList();
				for(int i = 0; i < CommandCollection.FISH_ROOM_USER_MAX; ++i) {
					if(userList[i] != -1) {
						sqe = SocketQueue.getInstance().get(userID);
						sendMessage(sqe.getChannel(), ProtoTypeEnum.SFishChapter, fishChapter.build().toByteArray());//登录成功返回用户数据
					}
				}
			}
		} else {
			user.setRoomName(null);
			//抛出异常
			errorMessage(channel, ErrorCodeEnum.NoFoundFishRoomError);
		}
	}
	
	private SFishChapter.Builder getFishChapter(FishRoom fr) {
		//向客户端返回房间信息
		SFishChapter.Builder fishChapter = SFishChapter.newBuilder();
		fishChapter.setRoomID(fr.getRoomID());
		fishChapter.setStartTime(fr.getStartTime());
		fishChapter.setCurrentTime(ActionCollection.getTime());
		fishChapter.setChapterID(fr.getChapterID());
		ArrayList<Integer> fishList = fr.getFishList();
		int i = 0;
		for(; i < fishList.size(); ++i) {
			fishChapter.addFishList(fishList.get(i));
		}
		int[] userList = fr.getUserList();
		for(i = 0; i < CommandCollection.FISH_ROOM_USER_MAX; ++i) {
			fishChapter.addUserList(userList[i]);
		}
		return fishChapter;
	}
	
}
