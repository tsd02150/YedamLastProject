package com.yedam.app.stock.mapper;

import java.util.List;
import java.util.Map;

import com.yedam.app.member.service.ItemVO;

public interface StockMapper {
	// 카테고리 선택
	public List<Map<String,Object>> getThemeList(String code);
	// 종목정보조회
	public ItemVO getItemInfo(String code);
}
