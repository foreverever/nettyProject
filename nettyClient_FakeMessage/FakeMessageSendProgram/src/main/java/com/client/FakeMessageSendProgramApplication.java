package com.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.client.main.FakeMessageInputProgram;
import com.client.main.FakeMessageSendProgramClient;

@SpringBootApplication
public class FakeMessageSendProgramApplication {
	
	public static void main(String[] args) {
		
		//서버에게 FAKE 데이터를 보내기 위한 input data를 받음
		FakeMessageInputProgram fakeMessageInputProgram = new FakeMessageInputProgram();
		fakeMessageInputProgram.run();
		
		ConfigurableApplicationContext context = SpringApplication.run(FakeMessageSendProgramApplication.class, args);
		context.getBean(FakeMessageSendProgramClient.class).run();

	}
	

}
