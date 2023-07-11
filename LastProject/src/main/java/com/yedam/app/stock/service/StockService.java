package com.yedam.app.stock.service;

import java.util.List;
import java.util.Map;

public interface StockService {
	public List<Map<String,Object>> getThemeList(String code);
	public ItemVO getItemInfo(String value);
	public List<Map<String, Object>> autocomplete(Map<String, Object> paramMap);
	public Map<String,Object> autoInfo(String value);
	public List<StockVO> allItemList();
	public int allItemCnt();
	public List<InqVO> inqChart();
	public List<StockVO> getIntStock(String membNo);
}
