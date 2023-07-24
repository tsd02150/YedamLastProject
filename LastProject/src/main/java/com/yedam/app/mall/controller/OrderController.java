package com.yedam.app.mall.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yedam.app.mall.service.BasketService;
import com.yedam.app.mall.service.BasketVO;
import com.yedam.app.mall.service.MallService;
import com.yedam.app.mall.service.OrderService;
import com.yedam.app.mall.service.OrderVO;
import com.yedam.app.member.service.MembVO;
import com.yedam.app.security.service.UserVO;

@Controller
@RequestMapping("mall")
public class OrderController {

	@Autowired
	MallService mallService;
	
	@Autowired
	BasketService basketService;
	
	@Autowired
	OrderService orderService;
	
	@GetMapping("orderList")
	public String orderList(Model model, BasketVO bskVO, MembVO membVO, OrderVO ordVO, HttpSession session) {
		
		UserVO mem = (UserVO) session.getAttribute("loggedInMember");
		model.addAttribute("member", membVO.getMembNo());
		
		
		model.addAttribute("orderList", orderService.getOrderList(ordVO));
		System.out.println(model);
		
		
		return "mall/orderList";
		
	}
	
	@GetMapping("orderCheck")
	public String orderCheck(Model model, OrderVO ordVO) {
		
		return "mall/orderCheck";
		
	}

}
