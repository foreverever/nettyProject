package com.client.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.client.codec.FakeMessageCodec;
import com.client.handler.FakeMessageInboundHandler;
import com.client.handler.FakeMessageOutboundHandler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutException;
import io.netty.handler.timeout.ReadTimeoutHandler;

@Component
class FakeMessageSendProgramInitializer extends ChannelInitializer<Channel> {
	
	@Autowired
	private FakeMessageCodec messageCodec;
	
	@Autowired
	private FakeMessageInboundHandler messageInboundHandler;
	
	@Autowired
	private FakeMessageOutboundHandler messageOutboundHandler;
	
	private final Logger logger = LoggerFactory.getLogger(FakeMessageSendProgramInitializer.class);

	@Override
	protected void initChannel(Channel channel) {
		ChannelPipeline pipeline = channel.pipeline();
		try {
			pipeline.addLast(new ReadTimeoutHandler(10));
			pipeline.addLast(new LoggingHandler(LogLevel.INFO));
			pipeline.addLast(messageCodec);
			pipeline.addLast(messageOutboundHandler);
			pipeline.addLast(messageInboundHandler);

			
		}
		catch(NullPointerException e) {
			logger.error("객체가 autowired가 되었는지 확인하세요.");
		}

	}

}
