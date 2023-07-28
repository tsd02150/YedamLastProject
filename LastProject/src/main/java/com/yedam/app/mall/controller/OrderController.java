package com.yedam.app.mall.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yedam.app.mall.service.BasketService;
import com.yedam.app.mall.service.BasketVO;
import com.yedam.app.mall.service.MallService;
import com.yedam.app.mall.service.OrderService;
import com.yedam.app.mall.service.OrderVO;
import com.yedam.app.member.service.ChargeVO;
import com.yedam.app.member.service.MembVO;
import com.yedam.app.member.service.MemberService;
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
	
	@Autowired
	MemberService membService;
	
	@GetMapping("orderList")
	public String orderList(Model model, BasketVO bskVO, MembVO membVO, OrderVO ordVO, HttpSession session) {
		
		//UserVO mem = (UserVO) session.getAttribute("loggedInMember");
		String membNo = ((UserVO) session.getAttribute("loggedInMember")).getMembNo();
		UserVO mem = (UserVO) session.getAttribute("loggedInMember");
		model.addAttribute("member", membVO.getMembNo());
		model.addAttribute("membNo", membNo);
		model.addAttribute("mem", mem);
		//System.out.println("memInfo"+membNo);
		List<OrderVO> orderList = new ArrayList<OrderVO>();
		List<OrderVO> membInfo = new ArrayList<OrderVO>();
		orderList = orderService.getOrderList(membNo);
		membInfo = orderService.getMemInfo(membNo);
		System.out.println("주문리스트 : " + orderList);
		model.addAttribute("orderList", orderList);
		model.addAttribute("membInfo", membInfo);
		System.out.println(membInfo);
		//model.addAttribute("orderList", orderService.getOrderList(ordVO));
		
		return "mall/orderList";
		
	}
	
	//결제 성공
	@GetMapping("orderCheck")
	public String orderCheck(
			/* @RequestParam(value="amount") int amount, */ HttpSession session, ChargeVO chargeVO, OrderVO ordVO, Model model, MembVO membVO) {
		/*
		 * UserVO mem = (UserVO) session.getAttribute("loggedInMember");
		 * chargeVO.setMembNo(mem.getMembNo()); //chargeVO.setChagPrc(amount);
		 * membVO.setId(mem.getId());
		 * 
		 * //결제 정보 저장, 등록 //포인트적립 membService.insertCharge(chargeVO, membVO);
		 * orderService.insertOrder(ordVO, membVO); //수정된 정보 세션에 다시 저장.
		 * mem.setPoint(membVO.getPoint()); session.setAttribute("loggedInMember", mem);
		 * model.addAttribute("membList", membVO); //update된 회원 정보
		 */
		return "mall/orderCheck";
	}
	
	
	// 결제 취소 페이지
	@GetMapping("orderCancel")
	public String orderCancel(Model model, OrderVO ordVO) {
		
		
		
		return "mall/orderCancel";
	}

}
