package org.jiira.data.netty.tcp;

import org.apache.log4j.Logger;
import org.jiira.utils.SASocketMessage;
import org.jiira.utils.SystemConfig;

import core.factory.worker.SADataWorker;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class SocketDataWorker extends SADataWorker {
	private static final Logger log = Logger.getLogger(SocketDataWorker.class);

	/** 用于分配处理业务线程的线程组个数 */
	protected static final int BIZGROUPSIZE = Runtime.getRuntime().availableProcessors() * 2; // 默认
	/** 业务出现线程大小 */
	protected static final int BIZTHREADSIZE = 4;

	private static final EventLoopGroup bossGroup = new NioEventLoopGroup(BIZGROUPSIZE);
	private static final EventLoopGroup workerGroup = new NioEventLoopGroup(BIZTHREADSIZE);

	public SocketDataWorker(String name, String command) {
		super(name, command);
	}

	public void dispatchEvent(SASocketMessage message) {
		// messages.add(sm);
		dispatchEvent(getCommand() + message.getType(), message.getMessage(), message.getChannel());
	}

	public void startup() {
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(bossGroup, workerGroup);
		bootstrap.channel(NioServerSocketChannel.class);
		bootstrap.childHandler(new SocketInitializer(this));
		bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000);
		bootstrap.bind(SystemConfig.instance().getPort()).awaitUninterruptibly();
		log.info("TCP服务器已启动, 监听端口 : " + SystemConfig.instance().getPort());
	}
}
