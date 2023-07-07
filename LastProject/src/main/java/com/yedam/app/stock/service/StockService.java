package com.yedam.app.stock.service;

import java.util.List;
import java.util.Map;

import com.yedam.app.member.service.ItemVO;

public interface StockService {
	public List<Map<String,Object>> getThemeList(String code);
	public ItemVO getItemInfo(String code);
}
