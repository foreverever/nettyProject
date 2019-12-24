package com.client.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.client.codec.MessageCodec;
import com.client.handler.MessageInboundHandler;
import com.client.handler.MessageOutboundHandler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

@Component
class MessageSendProgramInitializer extends ChannelInitializer<Channel> {
	
	@Autowired
	private MessageCodec messageCodec;
	
	@Autowired
	private MessageInboundHandler messageInboundHandler;
	
	@Autowired
	private MessageOutboundHandler messageOutboundHandler;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	protected void initChannel(Channel channel) {
		ChannelPipeline pipeline = channel.pipeline();
		try {
			pipeline.addLast(new LoggingHandler(LogLevel.INFO));
			pipeline.addLast(messageCodec);
			pipeline.addLast(messageOutboundHandler);
			pipeline.addLast(messageInboundHandler);
			pipeline.addLast(new ReadTimeoutHandler(10));
		}
		catch(NullPointerException e) {
			e.printStackTrace();
			logger.error("initChannel에서 Handler나 codec의 객체 정보를 받지 못함.");
		}
	}

}
