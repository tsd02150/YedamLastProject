package com.yedam.app.mall.controller;

import java.util.ArrayList;
import java.util.List;

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

	// 장바구니 정보 갖고오기
	@PostMapping("getBasketList")
	@ResponseBody
	public List<BasketVO> getBasketList(BasketVO bskVO) {

		return basketService.getBasketInfoList(bskVO);
	}

	@GetMapping("basketList")
	public String basketList(Model model, HttpSession session) {

		String memNo = ((UserVO) session.getAttribute("loggedInMember")).getMembNo();
		String prdtNo = ((UserVO) session.getAttribute("loggedInMember")).getPrdtNo();
		String bskNo = ((UserVO) session.getAttribute("loggedInMember")).getBskNo();
		model.addAttribute("bskNo", bskNo);
		model.addAttribute("membNo", memNo);
		model.addAttribute("prdtNo", prdtNo);
		List<BasketVO> list = new ArrayList<BasketVO>();
		list = basketService.getBasketList(memNo);

		model.addAttribute("basketList", list);

		return "mall/basketList";
	}

	@PostMapping("basketList")
	@ResponseBody
	public String addCartItem(BasketVO bskVO, HttpSession session) {
		UserVO mem = (UserVO) session.getAttribute("loggedInMember");

		if (mem == null) {
			return "fail"; // 로그인되지 않은 경우 처리
		}
		
		bskVO.setMembNo(mem.getMembNo());
		basketService.baskekCheck(bskVO);
		boolean result;
		
		if(basketService.baskekCheck(bskVO) == null) {
			
				result = basketService.addCartItem(bskVO);
			
		}else {
				basketService.basketUpdate(bskVO);
		}
		
		result = true;
		
		if (result) {
			return bskVO.getPrdtNo(); // 상품 번호 반환
		} else {
			return "fail";
		}
	}

	// 장바구니 수량 변경
	@PostMapping("updateCnt")
	@ResponseBody
	public String updateCnt(BasketVO bskVO) {
		System.out.println("장바구니 수량 확인" + bskVO);
		if (basketService.updateCnt(bskVO)) {
			return "success";
		} else {
			return "fail";
		}
	}

	// 장바구니 전체삭제
	@PostMapping("deleteAllBasket")
	@ResponseBody
	public String deleteAllBasket(BasketVO bskVO, HttpSession session) {
		
		UserVO mem = (UserVO) session.getAttribute("loggedInMember");
		bskVO.setMembNo(mem.getMembNo());
		
		System.out.println(bskVO);
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
		System.out.println(bskVO);
		if (basketService.deleteBasket(bskVO.getBskNo())) {
			return "success";
		} else {
			return "fail";
		}
	}

}