package com.yedam.app.stock.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
	
	private SimpMessagingTemplate template;
	
	@Autowired 
	public StockServiceImpl(SimpMessagingTemplate template) {
		this.template = template;
	}
	
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
		Integer point = stockMapper.getUserPoint(membNo);
		System.out.println(point + "pppppp");
		PossStockVO vo = stockMapper.getPossStock(itemNo, membNo);
		
		// 초기 값이 없을때
		if(vo == null) {
			vo = new PossStockVO();
			vo.setPoint(point);
			vo.setRate(0);
			vo.setCnt(0);
		}
		
		System.out.println(vo + "zzzs");
		
		return vo;
	}

	//주식 주문 프로시저 와 체결
	@Override
	public Map<String,Object> callOrderProd(Map<String, Object> params) {
		Map<String,Object> map = new HashMap<>(); // return map
		Map<String ,Object> taMap = new HashMap<>(); // 체결 파라미터 맵
		
		stockMapper.callOrderProd((Map<String, Object>) params); // 주문 프로시저
		
		Integer result = (Integer) params.get("order_result"); // 결과 반환
		
		
		if(result == 1) {
			PossStockVO vo = stockMapper.getPossStock((String)params.get("order_item_no"),(String) params.get("order_memb_no"));
			Integer point = stockMapper.getUserPoint((String) params.get("order_memb_no"));
			if(vo == null) {
				vo = new PossStockVO();
				vo.setPoint(point);
				vo.setRate(0);
				vo.setCnt(0);
			}
			map.put("code", "success");
			map.put("message", "주문에 성공했습니다!!");
			map.put("possStock", vo);
		}else if(result == 0) {
			map.put("code", "fail");
			map.put("message", "주문에 실패했습니다..");
		}
		
		// 체결 프로시저
		String orderNo = (String)params.get("insert_after_no"); // 체결된 아이디
		System.out.println(orderNo);
		Date orderDt = stockMapper.getOrderDt(orderNo);
		System.out.println(orderDt);
		taMap.put("order_type",(String) params.get("order_type")); // 주문종류
		taMap.put("order_dt", orderDt);
		taMap.put("ta_result", null);
		taMap.put("error_one", null);
		taMap.put("error_two", null);
		taMap.put("error_thr", null);
		taMap.put("seller", null);
		taMap.put("buyer", null);
		stockMapper.callTaProd(taMap);
		Integer taResult = (Integer) taMap.get("ta_result");
		String errorOne = (String) taMap.get("error_one");
		String errorTwo = (String) taMap.get("error_two");
		String errorThr = (String) taMap.get("error_thr");
		
		String seller = (String) taMap.get("seller");
		String buyer = (String) taMap.get("buyer");
		
		System.out.println("error_one : " +errorOne);
		System.out.println("error_two : " +errorTwo);
		System.out.println("error_thr : " +errorThr);
		if(taResult == 1) {
			System.out.println("taResult : 성공 ");
			//  실시간 알림
			System.out.println("매도자 : " + seller + " 매수자 : " + buyer);
			if(!seller.equals("none")) {
			sendOrderResult (seller , "매도주문이 체결되었습니다" );
			sendOrderResult (buyer , "매수주문이 체결되었습니다" );
			}
		}else if (taResult == 0) {
			System.out.println("taResult : 실패 ");
		}
		return map;
	}
	
	// 체결시 실시간 알람전송
	public void sendOrderResult( String membNo , String text) {
		System.out.println(membNo + " 알림 시행");
		String destination = "/stock/alarm/"+membNo;
		System.out.println(destination);
		this.template.convertAndSend(destination, text);
	}
	
	
	
	
	
}
