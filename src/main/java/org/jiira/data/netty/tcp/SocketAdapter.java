package org.jiira.data.netty.tcp;

import org.jiira.utils.SASocketMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class SocketAdapter extends ChannelInboundHandlerAdapter {
    private Logger log = LoggerFactory.getLogger(SocketAdapter.class);
	private SocketDataWorker handlerDispatcher;

	public SocketAdapter(SocketDataWorker handlerDispatcher) {
		log.info("SocketAdapter : ");
		this.handlerDispatcher = handlerDispatcher;
	}
	@Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent)evt;
            if (event.state()== IdleState.READER_IDLE){
                System.out.println("关闭这个不活跃通道！");
                SocketQueue.getInstance().remove(ctx);
            }
        }else {
            super.userEventTriggered(ctx,evt);
        }
    }

	@Override  
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		SASocketMessage sm = (SASocketMessage)msg;
		sm.setChannel(ctx);
		handlerDispatcher.dispatchEvent(sm);
    }
  
    @Override  
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		//log.info("channelReadComplete");
        ctx.flush();
    }
    
    ///
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
    	log.info("handlerAdded");
    }
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		//SocketQueue.getInstance().removeElement(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    	log.info("channelActive");
    }

    /**
     * 不活跃时关闭
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    	SocketQueue.getInstance().remove(ctx);
    	log.info("channelInactive");
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    	log.info("exceptionCaught");
    }
}
