package com.yedam.app;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.yedam.app.stock.mapper.StockMapper;
import com.yedam.app.stock.service.ItemInfoVO;
import com.yedam.app.stock.service.StockService;

@SpringBootTest
class LastProjectApplicationTests {

	@Autowired
	StockService stockService;
	
	private SimpMessagingTemplate template;
	
	@Autowired 
	public LastProjectApplicationTests(SimpMessagingTemplate template) {
		this.template = template;
	}
	
	//@Test
	void stompJSTest() {
		this.template.convertAndSend("/stock/alarm/mem-2","test");
	}
	
	@Test
	public void scheduler() {
		stockService.schedulerJob();
	}
}
