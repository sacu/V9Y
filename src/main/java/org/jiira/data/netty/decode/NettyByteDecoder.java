package org.jiira.data.netty.decode;

import java.util.List;

import org.jiira.utils.CommandCollection;
import org.jiira.utils.CommandCollection.ProtoTypeEnum;
import org.jiira.utils.SASocketMessage;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * 
 * @author Time
 * 二进制解码类
 */
public class NettyByteDecoder extends ByteToMessageDecoder {
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		int len = in.readUnsignedShortLE();
		int typeIndex = in.readUnsignedShortLE();
		ProtoTypeEnum type = ProtoTypeEnum.values()[typeIndex];
		byte[] bytes = new byte[len - CommandCollection.SOCK_TYPE_LENGTH];
		in.readBytes(bytes);
		out.add(new SASocketMessage(type, len, CommandCollection.getDataModel(type, bytes)));
	}
}
