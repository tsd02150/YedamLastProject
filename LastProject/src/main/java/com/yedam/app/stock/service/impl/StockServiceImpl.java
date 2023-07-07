package com.yedam.app.stock.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.stock.mapper.StockMapper;
import com.yedam.app.stock.service.StockService;

@Service
public class StockServiceImpl implements StockService {
	
	@Autowired
	StockMapper stockMapper;

	@Override
	public List<Map<String,Object>> getThemeList(String code) {
		return stockMapper.getThemeList(code);
	}
	
	
	
	
}
