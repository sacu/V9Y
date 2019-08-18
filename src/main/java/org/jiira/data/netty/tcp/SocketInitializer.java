package org.jiira.data.netty.tcp;

import java.util.concurrent.TimeUnit;

import org.jiira.data.netty.decode.NettyByteDecoder;
import org.jiira.data.netty.encode.NettyByteEncoder;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class SocketInitializer extends ChannelInitializer<SocketChannel> {
	@SuppressWarnings("unused")
	private int timeout = 3600;

	private SocketDataWorker handlerDispatcher;

	public SocketInitializer(SocketDataWorker handlerDispatcher) {
		this.handlerDispatcher = handlerDispatcher;
	}

	public void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new IdleStateHandler(5000, 5000, 5000,TimeUnit.MILLISECONDS));//顺序必须在第一个不然不触发……还没来及查原理
		pipeline.addLast(new NettyByteDecoder());// 解码 拆箱
		pipeline.addLast(new NettyByteEncoder());// 编码 装箱
		pipeline.addLast(new SocketAdapter(handlerDispatcher));
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
}
