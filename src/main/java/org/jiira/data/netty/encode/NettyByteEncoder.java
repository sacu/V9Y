package org.jiira.data.netty.encode;


import org.jiira.utils.SASocketMessage;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class NettyByteEncoder extends MessageToByteEncoder<SASocketMessage> {
	
	@Override
	protected void encode(ChannelHandlerContext ctx, SASocketMessage sm, ByteBuf out) throws Exception {
		out.writeShortLE(sm.getLength());
		out.writeShortLE(sm.getType().ordinal());
		out.writeBytes((byte[])sm.getMessage());
	}
}
