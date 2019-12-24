package com.client.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.ReadTimeoutException;

@Component
public class FakeMessageInboundHandler extends SimpleChannelInboundHandler<String> {

	private final Logger logger = LoggerFactory.getLogger(FakeMessageInboundHandler.class);

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error("Inbounder exception caught : {}" , cause.toString());
		if(cause instanceof ReadTimeoutException) {
			ctx.disconnect();
		}
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.info("Channel Activate");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		logger.info("Channel Inactivate");
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String message) throws Exception {
		logger.info("operate channel read0");
		String statusCode = message.substring(0, 4);
		logger.info("statuscode : {}" , statusCode);
		switch (statusCode) {
		case "OACK":
			ctx.write("send");
			break;

		case "NACK":
			String errorCode = message.substring(5, 8);
			ctx.write("cksd"+errorCode);
			break;
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}


	
}
