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
	public List<StockVO> allItemList() {
		return stockMapper.allItemList();
	}

	@Override
	public List<InqVO> inqChart() {
		return stockMapper.inqChart();
	}

	@Override
	public int allItemCnt() {
		return stockMapper.allItemCnt();
	}

	@Override
	public List<StockVO> getIntStock(String membNo) {
		return stockMapper.getIntStock(membNo);
	}

	@Override
	public List<StockVO> insertInterestItem(String membNo, String itemNo) throws Exception {
		if(!interestItemCheck(membNo, itemNo)) throw new Exception();
		int result = stockMapper.insertInterestItem(membNo, itemNo);
		List<StockVO> list;
		if(result > 0) {
			 list = stockMapper.getIntStock(membNo);
		}else {
			throw new Exception();
		}
		return list;
	}
	
	public boolean interestItemCheck(String membNo, String itemNo) {
		String check = stockMapper.intItemCheck(membNo, itemNo);
		return check == null;
	}

	@Override
	public String deleteIntItem(String membNo, String itemNo) {
		String message;
		int result = stockMapper.deleteIntItem(membNo, itemNo);
		
		if(result > 0) {
			message = "관심종목에서 제거 되었습니다.";
		}else {
			message = "제거에 실패했습니다";
		}
		return message;
	}
	
	
	
}
