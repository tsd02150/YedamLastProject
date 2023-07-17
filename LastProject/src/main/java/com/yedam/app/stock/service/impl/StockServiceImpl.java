package com.yedam.app.stock.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.community.service.BoardVO;
import com.yedam.app.stock.mapper.StockMapper;
import com.yedam.app.stock.service.InqVO;
import com.yedam.app.stock.service.ItemVO;
import com.yedam.app.stock.service.PossStockVO;
import com.yedam.app.stock.service.StockService;
import com.yedam.app.stock.service.StockVO;

@Service
public class StockServiceImpl implements StockService {
	
	@Autowired
	StockMapper stockMapper;
	
	//테마리스트 가져오기
	@Override
	public List<Map<String,Object>> getThemeList(String code) {
		return stockMapper.getThemeList(code);
	}
	
	//종목정보 가져오기
	@Override
	public ItemVO getItemInfo(String value) {
		return stockMapper.getItemInfo(value);
	}
	// 아이템번호로 종목정보가져오기
	@Override
	public StockVO itemNoGetInfo(String itemNo) {
		return stockMapper.itemNoGetInfo(itemNo);
	}
	
	//자동완성기능
	@Override
	public List<Map<String, Object>> autocomplete(Map<String, Object> paramMap) {
		return stockMapper.autocomplete(paramMap);
	}
	
	
	@Override
	public Map<String, Object> autoInfo(String value) {
		return stockMapper.autoInfo(value);
	}
	
	//모든종목리스트
	@Override
	public List<StockVO> allItemList() {
		return stockMapper.allItemList();
	}
	
	//인기검색순위
	@Override	
	public List<InqVO> inqChart() {
		return stockMapper.inqChart();
	}

	//모든 종목 개수
	@Override
	public int allItemCnt() {
		return stockMapper.allItemCnt();
	}
	
	//관심종목 리스트가져오기
	@Override
	public List<StockVO> getIntStock(String membNo) {
		return stockMapper.getIntStock(membNo);
	}
	
	//관심종목추가
	@Override
	public Map<String,Object> insertInterestItem(String membNo, String itemNo) {
		
		Map<String , Object> map = new HashMap<>(); ;
		String message;
		
		//넘겨받은 인자로 중복체크
		if(!interestItemCheck(membNo, itemNo)) {
			message ="이미 관심종목에 있는 종목입니다.";
			map.put("code", "warning");
			map.put("msg", message);
			
			return map;
		}
		// 관심종목 추가
		int result = stockMapper.insertInterestItem(membNo, itemNo);
		List<StockVO> list;
		
		if(result > 0) {
			 list = stockMapper.getIntStock(membNo);
			 message = "관심종목 추가에 성공했습니다!";
			 map.put("code", "success");
			 map.put("msg", message);
			 map.put("list", list);
		}else {
			 message = "관심종목 추가에 실패했습니다..";
			 map.put("code","fail");
			 map.put("msg", message);
		}
		return map;
	}
	
	//관심종목 중복 체크기능
	public boolean interestItemCheck(String membNo, String itemNo) {
		String check = stockMapper.intItemCheck(membNo, itemNo);
		return check == null;
	}
	
	//관심종목삭제
	@Override
	public Map<String,Object> deleteIntItem(String membNo, String itemNo) {
		Map<String,Object> map = new HashMap<>();
		String message;
		int result = stockMapper.deleteIntItem(membNo, itemNo);
		
		if(result > 0) {
			message = "관심종목에서 제거 되었습니다.";
			map.put("code", "success");
			map.put("msg", message);
		}else {
			message = "제거에 실패했습니다";
			map.put("code","fail");
			map.put("msg",message);
		}
		return map;
	}

	//종목명으로 종목번호 받기
	@Override
	public String nmGetNo(String nm) {
		return stockMapper.nmGetNo(nm);
	}
	
	//증권별 게시판 리스트
	@Override
	public List<BoardVO> getScBoardList(String itemNo) {
		return stockMapper.getScBoardList(itemNo);
	}
	
	// 거래량 top 5 순위목록 가져오기
	@Override
	public List<StockVO> topVolChart() {
		return stockMapper.topVolChart();
	}

	//변동률 가져오기
	@Override
	public List<StockVO> getPrcPercent(String type) {
		return stockMapper.getPrcPercent(type);
	}
	
	//호가 데이터
	@Override
	public List<Map<String,Object>> orderTable(String type, String itemNo) {
		return stockMapper.orderTable(type, itemNo);
	}

	// 보유 주식수량 수익률 포인트 가져오기
	@Override
	public PossStockVO getPossStock(String itemNo, String membNo) {
		return stockMapper.getPossStock(itemNo, membNo);
	}

	//주식 주문 프로시저
	@Override
	public Map<String,Object> callOrderProd(Map<String, Object> params) {
		Map<String,Object> map = new HashMap<>();
		stockMapper.callOrderProd((Map<String, Object>) params);
		Integer result = (Integer) params.get("order_result");
		
		if(result == 1) {
			PossStockVO vo = stockMapper.getPossStock((String)params.get("order_item_no"),(String) params.get("order_memb_no"));
			map.put("code", "success");
			map.put("message", "주문에 성공했습니다!!");
			map.put("possStock", vo);
		}else if(result == 0) {
			map.put("code", "fail");
			map.put("message", "주문에 실패했습니다..");
		}
		
		return map;
	}

	
	
	
	
	
	
}
