package org.jiira.data.netty.tcp;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import core.utils.SALog;
import io.netty.channel.ChannelHandlerContext;

public class SocketQueue {
	private static SocketQueue instance;
	public static SocketQueue getInstance() {
		if (null == instance) {
			instance = new SocketQueue();
		}
		return instance;
	}
	//id map
	private Map<Integer, SocketQueueElement> idMapQueue;
	//channel map
	private Map<ChannelHandlerContext, SocketQueueElement> channelMapQueue;
	private SocketQueue(){
		idMapQueue = new ConcurrentHashMap<Integer, SocketQueueElement>();
		channelMapQueue = new ConcurrentHashMap<ChannelHandlerContext, SocketQueueElement>();
	}
	/**
	 * 添加用户连接对象
	 * @param userID
	 * @param channel
	 */
	public void put(Integer userID, ChannelHandlerContext channel){
		SocketQueueElement sqe = new SocketQueueElement(userID, channel);
		idMapQueue.put(userID, sqe);
		channelMapQueue.put(channel, sqe);
		SALog.info("用户:" + sqe.getUserID() + " 上线...");
	}
	/**
	 * 通过用户ID获取用户连接对象
	 * @param userID
	 * @return
	 */
	public SocketQueueElement get(Integer userID){
		if(idMapQueue.containsKey(userID)){
			return idMapQueue.get(userID);
		}
		return null;
	}
	/**
	 * 通过用户通道获取用户连接对象
	 * @param userID
	 * @return
	 */
	public SocketQueueElement get(ChannelHandlerContext channel){
		if(channelMapQueue.containsKey(channel)){
			return channelMapQueue.get(channel);
		}
		return null;
	}
	
	public Map<Integer, SocketQueueElement> getIDMapQueue(){
		return idMapQueue;
	}
	/**
	 * 清除在线数据
	 * @param sqe
	 */
	public void remove(SocketQueueElement sqe){
		if(channelMapQueue.containsKey(sqe.getChannel())){
			channelMapQueue.remove(sqe.getChannel());
			sqe.getChannel().close();
		}
		if(idMapQueue.containsKey(sqe.getUserID())){
			idMapQueue.remove(sqe.getUserID());
		}
		SALog.info("用户:" + sqe.getUserID() + " 离线...");
	}
	public void remove(ChannelHandlerContext channel) {
		SocketQueueElement sqe = get(channel);
		if(null != sqe) {
			remove(sqe);
		}
	}
}
