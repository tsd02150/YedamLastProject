package com.yedam.app.stock.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.member.service.MembVO;
import com.yedam.app.stock.service.InqVO;
import com.yedam.app.stock.service.ItemVO;
import com.yedam.app.stock.service.StockService;
import com.yedam.app.stock.service.StockVO;

@Controller
@RequestMapping("stock")
public class StockController {
	
	@ExceptionHandler(Exception.class)
	public String exceptionCatcher(Exception ex,Model m) {
		System.out.println("ExceptionHandler 발동");
		m.addAttribute("message","error");
		return "stock/itemChoice";
	}
	
	@Autowired
	StockService stockservice;

	// 종목선택 페이지 이동
	@GetMapping("itemListPage")
	public String itemListPage(Model m) {
		List<InqVO> listInq = stockservice.inqChart();
		m.addAttribute("inqList",listInq);
		return "stock/itemChoice";
	}
	
	//상세 차트 페이지로 이동
	@GetMapping("chart")
	public String chartPage(String itemNo,Model m, HttpSession session) {
		MembVO mem = (MembVO)session.getAttribute("loggedInMember");
		System.out.println("mem 의 형태는 무엇일까 ? :" + mem);
		String membNo = mem == null ? null : mem.getMembNo();
		
		List<StockVO> list = stockservice.getIntStock(membNo);
		System.out.println("list의형태는 무엇일까 ? : " + list);
		m.addAttribute("interestStock",list);
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
	public Map<String, Object> autocomplete(@RequestParam Map<String, Object> paramMap) throws Exception {

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
	public List<StockVO> insertIntItem(String membNo , String itemNo) throws Exception {
		List<StockVO> result = stockservice.insertInterestItem(membNo, itemNo);
		return result;
	}
	
	//관심종목 삭제기능
	@ResponseBody
	@PostMapping("deleteIntItem")
	public String deleteIntItem(String membNo , String itemNo) {
		String message = stockservice.deleteIntItem(membNo, itemNo);
		return message;
	}
}
