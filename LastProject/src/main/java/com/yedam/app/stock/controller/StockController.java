package com.yedam.app.stock.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.stock.service.ItemVO;
import com.yedam.app.stock.service.StockService;
import com.yedam.app.stock.service.StockVO;

@Controller
@RequestMapping("stock")
public class StockController {

	@Autowired
	StockService stockservice;

	// 종목선택 페이지 이동
	@GetMapping("itemListPage")
	public String itemListPage(Model m) {
		List<StockVO> list = stockservice.allItemList();
		m.addAttribute("itemList", list);
		return "stock/itemChoice";
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
	
	@GetMapping("main")
	public String itemListPage2() {
		return "main/main";
	}

}
