package com.yedam.app.stock.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.stock.mapper.StockMapper;
import com.yedam.app.stock.service.InqVO;
import com.yedam.app.stock.service.ItemVO;
import com.yedam.app.stock.service.StockService;
import com.yedam.app.stock.service.StockVO;

@Service
public class StockServiceImpl implements StockService {
	
	@Autowired
	StockMapper stockMapper;

	@Override
	public List<Map<String,Object>> getThemeList(String code) {
		return stockMapper.getThemeList(code);
	}

	@Override
	public ItemVO getItemInfo(String value) {
		return stockMapper.getItemInfo(value);
	}

	@Override
	public List<Map<String, Object>> autocomplete(Map<String, Object> paramMap) {
		return stockMapper.autocomplete(paramMap);
	}

	@Override
	public Map<String, Object> autoInfo(String value) {
		return stockMapper.autoInfo(value);
	}

	@Override
	public List<StockVO> allItemList(Integer page) {
		return stockMapper.allItemList(page);
	}

	@Override
	public List<InqVO> inqChart() {
		return stockMapper.inqChart();
	}

	@Override
	public int allItemCnt() {
		return stockMapper.allItemCnt();
	}
	
	
	
	
}
