package com.yedam.app.mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


import com.yedam.app.mall.service.MallService;
import com.yedam.app.mall.service.ProductVO;

@Controller
@RequestMapping("mall")
public class MallController {
	
	@Autowired
	MallService mallService;
	
	// 포인트몰 메인 페이지
	@GetMapping("mallMain")
	public String mallMain(Model model) {
		List<ProductVO> list = mallService.getProductList();
		model.addAttribute("product", list);
		return "mall/mallMain";
	}
	/*
	 * @GetMapping("{pid}") public String detail(@PathVariable("pid") String pid,
	 * Model model) {
	 * 
	 * //mallService.ViemcntUpdate(pid); Optional<Detail> result = mallService.
	 * 
	 * return "mall/getFarm"; }
	 */
	
	
	// 농산물 페이지
	@GetMapping("farmList")
	public String farmList(Model model) {
		List<ProductVO> farmList = mallService.getProductList();
		model.addAttribute("farmList", farmList);
		return "mall/farmList";
	}
	
	//농산물 상세 페이지
	@GetMapping("getFarm")
	public String farmDetail(Model model) {
		List<ProductVO> farmList = mallService.getProductList();
		model.addAttribute("farm", farmList);
		return "mall/getFarm";
	}

	// 수산물 페이지
	@GetMapping("seafoodList")
	public String seafoodList(Model model) {
		List<ProductVO> seafoodList = mallService.getProductList();
		model.addAttribute("seafoodList", seafoodList);
		return "mall/seafoodList";
	}
	
	//장바구니
	@GetMapping("basketList")
	public String basket(Model model) {
		
		return "mall/basketList";
	}
	
	
}
