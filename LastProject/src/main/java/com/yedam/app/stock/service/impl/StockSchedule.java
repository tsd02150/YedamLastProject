package com.yedam.app.stock.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yedam.app.stock.mapper.StockMapper;
import com.yedam.app.stock.service.StockService;

@Component
public class StockSchedule {
	
	StockService stockService;
	
	@Autowired
	public StockSchedule(StockService stockService) {
		this.stockService = stockService;
	}
	
	
	//@Scheduled(cron = "0 0 18 * * *") 
	public void stockJob() {
	stockService.schedulerJob(); 
	}
	 
	
}
