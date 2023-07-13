package com.yedam.app;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.yedam.app.stock.service.StockService;

public class EnciptTest {	
	
	@Autowired
	StockService stockService;
	
	//@Test
	public void test() {
		BCryptPasswordEncoder scpwd = new BCryptPasswordEncoder();
		String password = scpwd.encode("1234");
		System.out.println(password);
	}
	
	@Test
	public void orderTest() {
		List<Map<String,Object>> list = stockService.orderTable("sell", "ite-1");
		System.out.println(list);
	}
}
