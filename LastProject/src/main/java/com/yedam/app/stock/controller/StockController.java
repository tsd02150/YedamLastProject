package com.yedam.app.stock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.stock.service.StockService;

@Controller
public class StockController {
	
	@Autowired
	StockService stockservice;
	
	// 종목선택 페이지 이동
	@GetMapping("stock/itemListPage")
	public String itemListPage() {
//		List<> list = stockService.getItemList
		return "stock/itemChoice";
	}
	
	//테마리스트 ajax
	@GetMapping("stock/themeList")
	@ResponseBody
	public List<String> themeList(String code){
		List<String> list = stockservice.getThemeList(code);
		return list;
	}
	
	@GetMapping("stock/main")
	public String itemListPage2() {
		return "domain/stock";
	}
}
