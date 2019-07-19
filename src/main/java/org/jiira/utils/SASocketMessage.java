package org.jiira.utils;

import org.jiira.utils.CommandCollection.ProtoTypeEnum;

import io.netty.channel.ChannelHandlerContext;

public class SASocketMessage {
	private ProtoTypeEnum type;
	private int length;
	private Object message;
	private ChannelHandlerContext channel;
	
	public SASocketMessage(ProtoTypeEnum type, int length, Object message){
		this.type = type;
		this.length = length;
		this.message = message;
	}

	public ProtoTypeEnum getType() {
		return type;
	}

	public void setType(ProtoTypeEnum type) {
		this.type = type;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}

	public ChannelHandlerContext getChannel() {
		return channel;
	}

	public void setChannel(ChannelHandlerContext channel) {
		this.channel = channel;
	}
}
