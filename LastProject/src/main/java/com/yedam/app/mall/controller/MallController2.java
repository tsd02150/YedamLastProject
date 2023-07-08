package com.yedam.app.mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.yedam.app.mall.service.MallService;
import com.yedam.app.mall.service.ProductVO;

@Controller
public class MallController {
	
	@Autowired
	MallService mallService;
	
	//포인트몰 메인 페이지
	@GetMapping("mall")
	public String mallMain()
	{
		return "mall/mallMain";
	}
	
	//농산물 페이지
		@GetMapping("farmList")
		public String farmList(ProductVO prdtVO, Model model) {
			model.addAttribute("farmList", mallService.getProductList());
			return "mall/farmList";
		}

	//수산물 페이지
	@GetMapping("seafoodList")
	public String seafoodList(ProductVO prdtVO, Model model) {
		model.addAttribute("seafoodList", mallService.getProductList());
		return "mall/seafoodList";
	}
	
}
