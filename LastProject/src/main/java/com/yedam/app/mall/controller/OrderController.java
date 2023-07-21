package com.yedam.app.mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yedam.app.mall.service.BasketService;
import com.yedam.app.mall.service.MallService;
import com.yedam.app.mall.service.OrderVO;

@Controller
@RequestMapping("mall")
public class OrderController {

	@Autowired
	MallService mallService;
	
	@Autowired
	BasketService basketService;
	
	@GetMapping("orderList")
	public String orderList() {
		
		return "mall/orderList";
		
	}
	
	@GetMapping("orderCheck")
	public String orderCheck(Model model, OrderVO ordVO) {
		
		return "mall/orderCheck";
		
	}

}
