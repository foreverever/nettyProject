package com.client.codec;

import java.nio.charset.Charset;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

@Component
@PropertySource(value = "classpath:/application.properties")
public class MessageCodec extends ByteToMessageCodec<String> {

	@Value("${headerLength}")
	private int headerLength;

	@Value("${statusCodeLength}")
	private int statusCodeLength;

	@Value("${errorCodeLength}")
	private int errorCodeLength;

	private final Logger logger = LoggerFactory.getLogger(MessageCodec.class);

	@Override
	protected void encode(ChannelHandlerContext ctx, String message, ByteBuf buf) throws Exception {
		logger.info("operate encode");
		buf.writeBytes(message.getBytes());
		logger.debug("send Packet : {}", message);
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> in) throws Exception {

		logger.info("operate decode");

		if (buf.readableBytes() < headerLength) {
			return;
		}

		String header = buf.readBytes(headerLength).toString(Charset.defaultCharset());

		int bodyLength = Integer.parseInt(header);

		// 헤더에 설정된 바디의 길이 만큼 데이터가 들어오지 않았을 경우
		if (buf.readableBytes() < bodyLength) {
			buf.resetReaderIndex();
		}
		
		// get statusCode
		String statusCode = buf.readBytes(statusCodeLength).toString(Charset.defaultCharset());

		String packetStatus = checkPacketStatus(buf, statusCode);
		
		if(packetStatus != null) {
			statusCode += packetStatus;
		}
		
		in.add(statusCode);
	}

	// packet의 Status code 검사
	private String checkPacketStatus(ByteBuf buf, String statusCode) throws Exception {

		if (statusCode.equals("NACK")) {
			String errorCode = buf.readBytes(errorCodeLength).toString(Charset.defaultCharset());
			String description = getErrorStatus(errorCode);
			logger.error("Packet Request Error : {}", description);
			return errorCode;
		}

		else {
			logger.debug("Packet Request Success : {}", statusCode);
			return null;
		}
	}

	private String getErrorStatus(String errorCode) {
		StringBuilder errorStatus = new StringBuilder();

		switch (errorCode.toUpperCase()) {

		case "WRDC":
			errorStatus.append("Wrong Data Content");
			break;

		case "WRSC":
			errorStatus.append("Wrong Statuscode Content");
			break;

		case "WRHC":
			errorStatus.append("Wrong Header Content");
			break;

		case "ORDA":
			errorStatus.append("Out of Range Data");
			break;
		}

		return errorStatus.toString();
	}

}
