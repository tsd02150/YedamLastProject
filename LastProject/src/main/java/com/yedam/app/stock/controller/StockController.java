package com.yedam.app.stock.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.member.service.ItemVO;
import com.yedam.app.stock.service.StockService;


@Controller
@RequestMapping("stock")
public class StockController {
	
	@Autowired
	StockService stockservice;
	
	// 종목선택 페이지 이동
	@GetMapping("itemListPage")
	public String itemListPage() {
//		List<> list = stockService.getItemList
		return "stock/itemChoice";
	}
	
	//테마리스트 ajax
	@GetMapping("themeList")
	@ResponseBody
	public List<Map<String,Object>> themeList(String code){
		List<Map<String,Object>> list = stockservice.getThemeList(code);
		System.out.println(list);
		return list;
	}
	
	//종목 정보
	@GetMapping("itemInfo")
	@ResponseBody
	public ItemVO getItemInfo(String code) {
		ItemVO vo = stockservice.getItemInfo(code);
		return vo;
	}
	
	@GetMapping("main")
	public String itemListPage2() {
		return "domain/stock";
	}
	
}
