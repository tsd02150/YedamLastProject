package com.yedam.app.stock.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.yedam.app.community.service.BoardVO;
import com.yedam.app.security.service.UserVO;
import com.yedam.app.stock.mapper.StockMapper;
import com.yedam.app.stock.service.AlarmVO;
import com.yedam.app.stock.service.InqVO;
import com.yedam.app.stock.service.ItemInfoVO;
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
		PossStockVO vo = stockMapper.getPossStock(itemNo, membNo);
		
		// 초기 값이 없을때
		if(vo == null) {
			vo = new PossStockVO();
			vo.setPoint(point);
			vo.setRate(0);
			vo.setCnt(0);
		}
		
		return vo;
	}

	//주식 주문 프로시저 와 체결
	@Override
	public Map<String,Object> callOrderProd(Map<String, Object> params ) {
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
		Date orderDt = stockMapper.getOrderDt(orderNo);
		taMap.put("order_type",(String) params.get("order_type")); // 주문종류
		taMap.put("order_dt", orderDt);
		taMap.put("ta_result", null);
		taMap.put("error_one", null);
		taMap.put("error_two", null);
		taMap.put("error_thr", null);
		taMap.put("seller", null);
		taMap.put("buyer", null);
		taMap.put("itemNm", null);
		stockMapper.callTaProd(taMap);
		
		Integer taResult = (Integer) taMap.get("ta_result");
		String seller = (String) taMap.get("seller");
		String buyer = (String) taMap.get("buyer");
		String itemNm = (String) taMap.get("itemNm");
		
		if(taResult == 1) {
			//  실시간 알림
			if(!seller.equals("none")) {
				
			sendOrderResult (seller , itemNm +"의 매도주문이 체결되었습니다" );
			sendOrderResult (buyer , itemNm + "의 매수주문이 체결되었습니다" );
			}
		}else if (taResult == 0) {
			System.out.println("taResult : 실패 ");
		}
		return map;
	}
	
	// 체결시 실시간 알람전송
	public void sendOrderResult( String membNo , String text) {
		stockMapper.insertStockAlarm(membNo, text); // 알람 테이블에 추가
		String destination = "/stock/alarm/"+membNo;
		this.template.convertAndSend(destination, text);
	}
	
	
	@Override
	public void schedulerJob() {
		// 장마감시간에 주문div 막는 div 생성
		closeMarket();
		
		// 당일 주가 정보
		List<ItemInfoVO> talist = stockMapper.todayItemInfo();
		
		
		// 오늘 체결 정보 없는 종목 찾기
		List<String> intList = new ArrayList<>();
		List<Integer> itemNos = new ArrayList<>();
		boolean[] chk = new boolean[47];
		
		for(ItemInfoVO vo : talist) {
			intList.add(vo.getItemNo().substring(4));
		}
		
		for(int i = 0 ; i <intList.size() ; i++) {
			chk[Integer.parseInt(intList.get(i))] = true;
		}
		
		for(int i = 1 ; i < chk.length ; i++) {
			if(chk[i] == false) {
				itemNos.add(i);
			}
		}
		// 정보가 없는 종목 먼저 insert
		List<ItemInfoVO> missingList = stockMapper.nonTaInfo(itemNos);
		// 정보가 있는 종목들은 insert
		for(ItemInfoVO vo : talist) {
			stockMapper.insertItemInfo(vo);
		}
				
		for(ItemInfoVO vo : missingList) {
			stockMapper.insertItemInfo(vo);
		}
		// 초기화 ( 조회수초기화 , 수량 남은 주문 반환 )
		Map<String,Object> map = new HashMap<>();
		map.put("del_result", null);
		map.put("error_one", null);
		map.put("error_two", null);
		map.put("error_thr", null);
		
		stockMapper.deleteJob(map);
	}
	
	// 조회수 증가
	@Override
	public void updateInq(String itemNo) {
		stockMapper.updateInq(itemNo);
	}
	// 일간 차트
	@Override
	public List<ItemInfoVO> dayChart(String itemNo) {
		return stockMapper.dayChart(itemNo);
	}
	// 주간,월간 차트
	@Override
	public List<ItemInfoVO> weekMonthChart(String itemNo, String type) {
		System.out.println("zz"+stockMapper.weekMonthChart(itemNo, type));
		return stockMapper.weekMonthChart(itemNo, type);
	}
	//미확인 알람 리스트
	@Override
	public List<AlarmVO> nonCheckedAlarm(String membNo) {
		return stockMapper.nonCheckedAlarm(membNo);
	}
	
	// 미확인 알람 확인 알람으로 바꾸기
	@Override
	public int stockAlmChk(String almNo) {
		int result = stockMapper.stockAlmChk(almNo);
		
		return result;
	}
	//알람삭제
	@Override
	public int deleteAlm(String almNo) {
		return stockMapper.deleteAlm(almNo);
	}
	
	// 현재 주가정보
	@Override
	public ItemInfoVO currentItemInfo(String itemNo) {
		return stockMapper.currentItemInfo(itemNo);
	}
	// 회원 포인트 가져오기
	@Override
	public Integer getPoint(String membNo) {
		return stockMapper.getPoint(membNo);
	}
	// 보유주식 리스트 가져오기
	@Override
	public List<StockVO> getPossList(String membNo) {
		return stockMapper.getPossList(membNo);
	}
	// 종목의 전날 종가 ( 상한가 하한가 설정 위함)
	@Override
	public int getClPrc(String itemNo) {
		return stockMapper.getClPrc(itemNo);
	}
	
	// 장마감시 실시간 알람전송
	public void closeMarket() {
		this.template.convertAndSend("/all", "close");
	}

	@Override
	public void testSend() {
		closeMarket();
		
	}
}
