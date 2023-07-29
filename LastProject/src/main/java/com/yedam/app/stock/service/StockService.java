package com.yedam.app.stock.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import com.yedam.app.community.service.BoardVO;


public interface StockService {
	public List<Map<String,Object>> getThemeList(String code);
	public ItemVO getItemInfo(String value);
	public StockVO itemNoGetInfo(String itemNo);
	public List<Map<String, Object>> autocomplete(Map<String, Object> paramMap);
	public Map<String,Object> autoInfo(String value);
	public List<StockVO> allItemList();
	public int allItemCnt();
	public List<InqVO> inqChart();
	public List<StockVO> getIntStock(String membNo);
	public Map<String,Object> insertInterestItem(String membNo , String itemNo);
	public Map<String,Object> deleteIntItem(String membNo ,String itemNo);
	public String nmGetNo(String nm);
	public List<BoardVO> getScBoardList(String itemNo);
	public List<StockVO> topVolChart();
	public List<StockVO> getPrcPercent(String type);
	public List<Map<String,Object>> orderTable(String type , String itemNo);
	public PossStockVO getPossStock(String itemNo ,String membNo);
	public Map<String,Object> callOrderProd(Map<String,Object> params );
	// 스케줄러 작업
	public void schedulerJob();
	public void updateInq(String itemNo);
	public List<ItemInfoVO> dayChart(String itemNo);
	public List<ItemInfoVO> weekMonthChart(String itemNo , String type);
	public List<AlarmVO> nonCheckedAlarm(String membNo);
	public int stockAlmChk(String almNo);
	public int deleteAlm(String almNo);
	public ItemInfoVO currentItemInfo(String itemNo);
	public Integer getPoint(String membNo);
	public List<StockVO> getPossList(String membNo);
}
