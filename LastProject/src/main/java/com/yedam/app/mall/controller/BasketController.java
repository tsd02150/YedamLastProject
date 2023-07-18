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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.mall.service.BasketService;
import com.yedam.app.mall.service.BasketVO;
import com.yedam.app.mall.service.OrderVO;
import com.yedam.app.member.service.MembVO;
import com.yedam.app.member.service.MemberService;

@Controller
@RequestMapping("mall")
public class BasketController {

	@Autowired
	BasketService basketService;
	
	@Autowired
	MemberService membService;
	
	/*
	 * // 카트 목록 불러오기
	 * 
	 * @GetMapping("/basketList") public String getBasketList(HttpSession session,
	 * Model model) throws Exception { //MembVO loggedInMember =
	 * membService.mainLoginCheck(membVO);
	 * 
	 * //session에 저장해두었던 userId String userId =
	 * (String)session.getAttribute("loggedInMember");
	 * 
	 * // DB에 저장되어있던 cartList
	 * 
	 * //String userId = (String) session.getAttribute("member"); //List<BasketVO>
	 * basketList = basketService.getBasketList(loggedInMember);
	 * model.addAttribute("basketList", basketList); return "mall/basketList"; }
	 */
     
     //카트리스트에서 주문하기
		/*
		 * @PostMapping("/basketList") public String order(HttpSession session, OrderVO
		 * order, @RequestParam(value = "chk[]") List<String> chArr) throws Exception {
		 * logger.info("order");
		 * 
		 * String userId = (String)session.getAttribute("member");
		 * 
		 * //주문번호(orderId) 생성을 위한 로직 Calendar cal = Calendar.getInstance(); int year =
		 * cal.get(Calendar.YEAR); String ym = year + new
		 * DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1); String ymd = ym +
		 * new DecimalFormat("00").format(cal.get(Calendar.DATE)); String subNum = "";
		 * 
		 * for(int i = 1; i <= 6; i ++) { subNum += (int)(Math.random() * 10); }
		 * 
		 * String orderId = ymd + "_" + subNum; //ex) 20200508_373063
		 * order.setOrderId(orderId); order.setUserId(userId);
		 * 
		 * service.orderInfo(order); //주문 테이블 insert
		 * 
		 * int cartNum = 0; for(String i : chArr){ cartNum = Integer.parseInt(i);
		 * System.out.println("cart -> CHK orderList : "+cartNum);
		 * System.out.println("cart -> orderId orderList : "+orderId);
		 * service.orderInfoDetails(orderId,cartNum); //주문 상세 테이블 insert
		 * service.cartDelete(cartNum); //체크되어 들어온 cart번호로 cart table delete }
		 * 
		 * 
		 * return "redirect:/shop/myPage"; }
		 */
	
	
	
	
	
	
	

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
