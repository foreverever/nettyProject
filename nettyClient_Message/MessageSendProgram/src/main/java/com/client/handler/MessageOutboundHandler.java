package com.client.handler;

import java.net.SocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.client.exception.WrongDataFormatException;
import com.client.exception.WrongHeaderFormatException;
import com.client.exception.WrongStatusCodeFormatException;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.util.internal.ThreadLocalRandom;

@Component
@PropertySource(value = "classpath:/application.properties")
public class MessageOutboundHandler extends SimpleChannelOutboundHandler<String> {

	@Value("${headerLength}")
	private int headerLength;

	@Value("${statusCodeLength}")
	private int statusCodeLength;

	@Value("${dataLength}")
	private int dataLength;

	static int cal = 0;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	protected void write0(ChannelHandlerContext ctx, String msg, ChannelPromise promise) throws Exception {

		logger.info("messageOutboundHandler write0!!");

		String packet = makeDataPacket();

		boolean isWrite = true;
		String command = msg.substring(0, 4);

		//packet의 이상유무 판단 msg
		if (command.equals("cksd")) {
			String errorCode = msg.substring(5, 8);
			if (!checkDataPacket(packet, errorCode)) {
				isWrite = false;
			}
		}

		if (isWrite) {
			ctx.writeAndFlush(packet);
		}
	}

	private String makeDataPacket() {
		// make Packet Header + Packet Status Code 총 8자리
		StringBuffer stringBuffer = new StringBuffer("0008DATA");

		// make Data 총 4자리
		int data = ThreadLocalRandom.current().nextInt(8) + 1;

		stringBuffer.append("000");

		stringBuffer.append(data);

		return stringBuffer.toString();
	}

	//packet check
	private boolean checkDataPacket(String packet, String errorCode) {
		
		String header = null;
		String statusCode = null;
		String data = null;
		
		try {
			switch (errorCode) {
			
			// packet의 header 검사
			case "WRHC":
				header = packet.substring(0, headerLength);
				if (!isNumber(header)) {
					throw new WrongHeaderFormatException("패킷의 Header가 Number가 아닙니다.");
				}
				break;

			// packet의 status code 검사
			case "WRSC":
				statusCode = packet.substring(headerLength + 1, statusCodeLength + headerLength);
				if (!statusCode.equals("DATA")) {
					throw new WrongStatusCodeFormatException("패킷의 Status Code가 DATA가 아닙니다.");
				}
				break;

			// packet의 data부분 검사
			case "WRDC":
				data = packet.substring(statusCodeLength + headerLength + 1,
						statusCodeLength + headerLength + dataLength);

				if (!isNumber(data)) {
					throw new WrongDataFormatException("패킷의 Data가 Number가 아닙니다.");
				}
				break;
			}

		}
		catch (WrongHeaderFormatException e) {
			e.printStackTrace();
			logger.error("header(body length) is not number / header : {}", header);
			return false;
		} catch (WrongStatusCodeFormatException e) {
			e.printStackTrace();
			logger.error("status code is not 'DATA' / statusCode : {}", statusCode);
			return false;
		} catch (WrongDataFormatException e) {
			e.printStackTrace();
			logger.error("data is not number / data : {}", data);
			return false;
		}

		return true;
	}

	private boolean isNumber(String input) {

		char[] inputArray = input.toCharArray();

		for (int idx = 0; idx < inputArray.length; idx++) {
			if ('0' > inputArray[idx] || inputArray[idx] > '9') {
				return false;
			}
		}

		return true;
	}

	@Override
	public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress,
			ChannelPromise promise) throws Exception {
		super.connect(ctx, remoteAddress, localAddress, promise);
	}
	
	@Override
	public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		System.out.println("데이터가 다 전송되어 프로그램을 종료합니다.");
	}

}
