package com.yedam.app.stock.mapper;

import java.util.List;
import java.util.Map;

public interface StockMapper {
	public List<Map<String,Object>> getThemeList(String code);

}
