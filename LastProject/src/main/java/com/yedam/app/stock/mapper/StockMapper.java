package com.yedam.app.stock.mapper;

import java.util.List;
import java.util.Map;

import com.yedam.app.stock.service.InqVO;
import com.yedam.app.stock.service.ItemVO;
import com.yedam.app.stock.service.StockVO;

public interface StockMapper {
	// 카테고리 선택
	public List<Map<String,Object>> getThemeList(String code);
	// 종목정보조회
	public ItemVO getItemInfo(String value);
	//자동완성
	public List<Map<String, Object>> autocomplete(Map<String, Object> paramMap);
	//자동완성사용 정보 출력
	public Map<String,Object> autoInfo(String value);
	// 모든 종목 리스트
	public List<StockVO> allItemList(Integer page);
	// 모든 종목 수
	public int allItemCnt();
	//조회수 순위 차트
	public List<InqVO> inqChart();
}
