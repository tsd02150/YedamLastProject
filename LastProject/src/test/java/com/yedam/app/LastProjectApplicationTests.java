package com.yedam.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@SpringBootTest
class LastProjectApplicationTests {

	private SimpMessagingTemplate template;
	
	@Autowired 
	public LastProjectApplicationTests(SimpMessagingTemplate template) {
		this.template = template;
	}
	
	@Test
	void stompJSTest() {
		this.template.convertAndSend("/stock/alarm/mem-2","test");
	}

}
