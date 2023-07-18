package com.yedam.app.mall.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.mall.service.BasketService;
import com.yedam.app.mall.service.BasketVO;
import com.yedam.app.member.service.MembVO;

@Controller
@RequestMapping("mall")
public class BasketController {

	@Autowired
	BasketService basketService;

	// 장바구니 리스트
	@GetMapping("basketList")
	public String baskeList(Model model, BasketVO bskVO, HttpSession session) throws Exception {
		
		final Logger logger = LoggerFactory.getLogger(BasketController.class.getName());
		
		logger.info("get basket list");
		
		Model userId = (Model)session.getAttribute("member");
		
//		List<BasketVO> basketList = basketService.getBasketList(userId);
//		model.addAttribute("basketList", basketList);
//		
		model.addAttribute("myInfo",session.getAttribute("loggedInMember"));
		//model.addAttribute("baskeList", basketService.getBasketList());
		
		MembVO myInfo = new MembVO();
		myInfo.setMembNo("noLogin");
		model.addAttribute("myInfo",myInfo);
		
		return "mall/basketList";
	}
	
	
	

	// 장바구니 전체삭제
	@PostMapping("deleteAllBasket")
	@ResponseBody
	public String deleteAllBasket(BasketVO bskVO) {

		if (basketService.deleteAllBasket(bskVO)) {
			return "success";
		} else {
			return "fail";
		}
	}
	
	
	
	
	
	
	
}
