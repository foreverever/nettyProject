package com.client.channel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;

@Component
@PropertySource(value = "classpath:/application.properties")
public class ClientChannel {

	@Value("${testingIP}")
	private String testingIP;

	@Value("${tempIP}")
	private String tempIP;

	@Value("${port}")
	private int port;

	private Channel channel;

	private static ClientChannel clientChannel;
	
	private Bootstrap bootstrap;

	private ClientChannel() {
		this.bootstrap = new Bootstrap();
	}

	public Bootstrap getBootstrap() {
		return bootstrap;
	}

	public void setBootstrap(Bootstrap bootstrap) {
		this.bootstrap = bootstrap;
	}

	public static ClientChannel getInstance() {
		if (clientChannel == null) {
			clientChannel = new ClientChannel();
		}

		return clientChannel;
	}

	public Channel getChannel(Bootstrap bootstrap) {
		if (channel == null) {
			try {
				channel = bootstrap.connect(tempIP, port).sync().channel();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return channel;
	}
	
	public void removeChannel() {
		
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

}
