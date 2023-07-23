package com.yedam.app.mall.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.mall.service.BasketService;
import com.yedam.app.mall.service.BasketVO;
import com.yedam.app.mall.service.MallService;
import com.yedam.app.member.service.MembVO;
import com.yedam.app.member.service.MemberService;
import com.yedam.app.security.service.UserVO;

@Controller
@RequestMapping("mall")
public class BasketController {

	@Autowired
	BasketService basketService;

	@Autowired
	MemberService membService;

	@Autowired
	MallService mallService;

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

	// 카트리스트에서 주문하기
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

	// 장바구니 페이지, 모델에 담아서 넘기기
	@GetMapping("basketList")
	public String basketList() {
		
		return "mall/basketList";
	}
	
	// 장바구니 정보 갖고오기

	@PostMapping("getBasketList")
	@ResponseBody
	public List<BasketVO> getBasketList(BasketVO bskVO) {
		List<BasketVO> basketItem = basketService.getBasketList(bskVO);

		return basketItem;
	}

	// 장바구니 리스트
	@GetMapping("allbasketItem")
	public Map<String, Object> allbasketItem(Model model, BasketVO bskVO, HttpSession session) throws Exception {

		Map<String, Object> map = new HashMap<>();
		List<BasketVO> list = basketService.getBasketList(bskVO);
		map.put("basketItem", list);

		UserVO mem = (UserVO) session.getAttribute("loggedInMember");

		if (mem != null) {
			model.addAttribute("interestPrdt", basketService.getIntPrdt(mem.getMembNo()));
		}

		if (session.getAttribute("loggedInMember") != null) {
			model.addAttribute("myInfo", session.getAttribute("loggedInMember"));
		} else {
			MembVO myInfo = new MembVO();
			myInfo.setMembNo("noLogin");
			model.addAttribute("myInfo", myInfo);
		}

		return map;
	}

	// 장바구니 추가
	@PostMapping("addCartItem")
	@ResponseBody
	public String addCartItem(BasketVO bskVO, Model model, HttpSession session) {

		UserVO mem = (UserVO) session.getAttribute("loggedInMember");
		
		bskVO.setMembNo(mem.getMembNo());

		boolean result = basketService.addCartItem(bskVO);

		if (result) {
			return bskVO.getPrdtNo();
		} else {

			return "fail";
		}
	
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
	
	// 장바구니 단건삭제
	@PostMapping("deleteBasket")
	@ResponseBody
	public String deleteBasket(BasketVO bskVO) {

		if (basketService.deleteBasket(bskVO.getPrdtNo())) {
			return "success";
		} else {
			return "fail";
		}
	}

}
