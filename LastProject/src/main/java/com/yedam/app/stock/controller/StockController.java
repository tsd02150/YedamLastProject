package com.yedam.app.stock.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.security.service.UserVO;
import com.yedam.app.stock.service.AlarmVO;
import com.yedam.app.stock.service.InqVO;
import com.yedam.app.stock.service.ItemInfoVO;
import com.yedam.app.stock.service.ItemVO;
import com.yedam.app.stock.service.PossStockVO;
import com.yedam.app.stock.service.StockOrderVO;
import com.yedam.app.stock.service.StockService;
import com.yedam.app.stock.service.StockVO;
// 김태연 2023-07-20 주식관리 매도매수 
@Controller
@RequestMapping("stock")
public class StockController {
	
	
	@Autowired
	StockService stockservice;

	// 종목선택 페이지 이동
	@GetMapping("itemListPage")
	public String itemListPage(Model m) {
		//인기검색어 순위
		List<InqVO> listInq = stockservice.inqChart();
		m.addAttribute("inqList",listInq);
		return "stock/itemChoice";
	}
	
	//상세 차트 페이지로 이동
	@GetMapping("chart")
	public String chartPage(String itemNo,Model m, HttpSession session) {
		// 조회수 증가
		stockservice.updateInq(itemNo);
		UserVO mem = (UserVO)session.getAttribute("loggedInMember");
		if(mem != null) {
			m.addAttribute("possStock",stockservice.getPossList(mem.getMembNo())); // 유저 보유종목 리스트
			m.addAttribute("interestStock",stockservice.getIntStock(mem.getMembNo())); // 유저관심종목리스트 
		}
		m.addAttribute("clPrc",stockservice.getClPrc(itemNo));
		m.addAttribute("boardList",stockservice.getScBoardList(itemNo)); // 종목게시판
		m.addAttribute("itemInfo",stockservice.itemNoGetInfo(itemNo));
		m.addAttribute("itemNo",itemNo);
		return "stock/chartPage";
	}

	// 테마리스트 ajax
	@GetMapping("themeList")
	@ResponseBody
	public List<Map<String, Object>> themeList(String code) {
		List<Map<String, Object>> list = stockservice.getThemeList(code);
		System.out.println(list);
		return list;
	}

	// 종목 정보
	@GetMapping("itemInfo")
	@ResponseBody
	public ItemVO getItemInfo(String value) {
		ItemVO vo = stockservice.getItemInfo(value);
		return vo;
	}

	// 자동완성 목록
	@PostMapping("autoComplete")
	@ResponseBody
	public Map<String, Object> autocomplete(@RequestParam Map<String, Object> paramMap)  {

		List<Map<String, Object>> resultList = stockservice.autocomplete(paramMap);
		paramMap.put("resultList", resultList);

		return paramMap;
	}
	
	//자동완성 목록 선택시 정보출력
	@ResponseBody
	@GetMapping("autoInfo")
	public Map<String,Object> autoInfo(String ctrg){
		Map<String,Object> map = stockservice.autoInfo(ctrg);
		return map;
	}
	
	//조회수 순위 차트
	@ResponseBody
	@GetMapping("inqChart")
	public List<InqVO> inqChart(){
		return stockservice.inqChart();
	}
	
	//모든 종목 리스트
	@ResponseBody
	@GetMapping("allItemList")
	public Map<String,Object> allItemList(){
		
		Map<String,Object> map = new HashMap<>();
		List<StockVO> list = stockservice.allItemList();
		map.put("itemList", list);
		return map;
	}
	
	
	//관심종목 추가기능
	@ResponseBody
	@PostMapping("insertIntItem")
	public Map<String,Object> insertIntItem(String membNo , String itemNo) {
		System.out.println(membNo + " " + itemNo + "zzzzzzzzzzzzzzzzzzzzzzzzzzz");
		Map<String,Object> map = stockservice.insertInterestItem(membNo, itemNo);
		return map;
	}
	
	//관심종목 삭제기능
	@ResponseBody
	@PostMapping("deleteIntItem")
	public Map<String,Object> deleteIntItem(String membNo , String itemNo) {
		return stockservice.deleteIntItem(membNo, itemNo);
	}
	
	//종목명으로 종목번호 받기
	@ResponseBody
	@GetMapping("nmGetNo")
	public String nmGetNo(String nm) {
		String itemNo = stockservice.nmGetNo(nm);
		return itemNo;
	}
	
	//유저 관심종목 변동
	@ResponseBody
	@PostMapping("ajaxUsetInt")
	public Map<String,Object> ajaxUserInt(String membNo ){
		List<StockVO> list = stockservice.getIntStock(membNo);
		Map<String,Object> map = new HashMap<>();
		map.put("list", list);
		return map;
	}
	//유저 보유종목 변동
	@ResponseBody
	@PostMapping("ajaxUserPoss")
	public Map<String,Object> ajaxUserPoss(String membNo ){
		List<StockVO> list = stockservice.getPossList(membNo);
		Map<String,Object> map = new HashMap<>();
		map.put("list", list);
		return map;
	}
	//변동률 데이터,거래량 데이터 가져오기
	@ResponseBody
	@GetMapping("getPercentage")
	public Map<String,Object> getPercentage(String type){
		List<StockVO> perList = stockservice.getPrcPercent(type);
		List<StockVO> volList = stockservice.topVolChart();
		Map<String,Object> map = new HashMap<>();
		map.put("perList", perList);
		map.put("volList",volList);
		return map;
	}
	
	// 호가 데이터
	@ResponseBody
	@GetMapping("orderTable")
	public List<Map<String,Object>> orderTable(String type, String itemNo){
		List<Map<String,Object>> list = stockservice.orderTable(type, itemNo);
		return list;
	}
	
	//아이템 번호로 정보가져오기
	@ResponseBody
	@GetMapping("getItemInfo")
	public StockVO itemNoGetInfo(String itemNo) {
		StockVO vo = stockservice.itemNoGetInfo(itemNo);
		return vo;
	}
	
	// 유저 보유주식 수량 수익률 (단건)
	@ResponseBody
	@PostMapping("possStock")
	public PossStockVO getPossStock(String membNo , String itemNo) {
		PossStockVO vo = stockservice.getPossStock(itemNo, membNo);
		return vo;
		
	}
	
	// 주문프로시저
	@ResponseBody
	@PostMapping("stockOrder")
	public Map<String,Object> stockOrder(StockOrderVO vo ){
		Map<String,Object> orderMap = new HashMap<>();
		orderMap.put("order_item_no", vo.getOrder_item_no());
		orderMap.put("order_memb_no", vo.getOrder_memb_no());
		orderMap.put("order_prc", vo.getOrder_prc());
		orderMap.put("order_rmn_cnt", vo.getOrder_rmn_cnt());
		orderMap.put("order_type", vo.getOrder_type());
		orderMap.put("order_result", null);
		orderMap.put("insert_after_no", null);
		Map<String,Object> map = stockservice.callOrderProd(orderMap);
		return map;
	}
	
	//일간 차트
	@ResponseBody
	@GetMapping("dayChart")
	public List<ItemInfoVO> getDayChart(String itemNo){
		List<ItemInfoVO> list = stockservice.dayChart(itemNo);
		ItemInfoVO vo = stockservice.currentItemInfo(itemNo);
		list.add(vo);
		return list;
	}
	//주간 , 월간 차트
	@ResponseBody
	@GetMapping("weekMonthChart")
	public List<ItemInfoVO> getWeekMonthChart(String itemNo , String type){
		System.out.println(type);
		System.out.println(itemNo);
		return stockservice.weekMonthChart(itemNo, type);
	}
	// 미확인 알람 가져오기
	@ResponseBody
	@GetMapping("getNonChkAlm")
	public List<AlarmVO> getNonChkAlm(String membNo){
		return stockservice.nonCheckedAlarm(membNo);
	}
	
	// 미확인 알람 확인 알람으로 바꾸기
	@ResponseBody
	@GetMapping("changeChk")
	public int changeChk(String almNo) {
		return stockservice.stockAlmChk(almNo);
	}
	// 알람삭제
	@ResponseBody
	@GetMapping("deleteAlm")
	public int deleteAlm(String almNo) {
		return stockservice.deleteAlm(almNo);
	}
	
	//session 포인트 갱신
	@ResponseBody
	@GetMapping("resetPoint")
	public void resetPoint(HttpSession session) {
		UserVO vo = (UserVO) session.getAttribute("loggedInMember");
		vo.setPoint(stockservice.getPoint(vo.getMembNo()));
		session.setAttribute("loggedInMember", vo);
	}
	
	// test
	@ResponseBody
	@GetMapping("test")
	public void testing() {
		stockservice.testSend();
	}
}
