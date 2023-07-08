package com.yedam.app.stock.service;

import java.util.List;
import java.util.Map;

public interface StockService {
	public List<Map<String,Object>> getThemeList(String code);
	public ItemVO getItemInfo(String value);
	public List<Map<String, Object>> autocomplete(Map<String, Object> paramMap);
	public Map<String,Object> autoInfo(String value);
	public List<StockVO> allItemList();
	public List<InqVO> inqChart();
}
