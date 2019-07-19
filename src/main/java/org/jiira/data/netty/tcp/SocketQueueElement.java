package org.jiira.data.netty.tcp;

import io.netty.channel.ChannelHandlerContext;

public class SocketQueueElement {
	private Integer userID;
	
	private ChannelHandlerContext channel;
	
	public SocketQueueElement(Integer userID, ChannelHandlerContext channel){
		this.userID = userID;
		this.channel = channel;
	}
	
	public Integer getUserID() {
		return userID;
	}

	public ChannelHandlerContext getChannel() {
		return channel;
	}
}
