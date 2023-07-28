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

//	// 장바구니 페이지 이거야!!!!!!!!!!!!!
	@GetMapping("basketList")
	public String basketList(Model model, HttpSession session) {
		// bskVO = basketService.getBasketList(bskVO.getPrdtNo());

		String memNo = ((UserVO) session.getAttribute("loggedInMember")).getMembNo();
		String prdtNo = ((UserVO) session.getAttribute("loggedInMember")).getPrdtNo();
		String bskNo = ((UserVO) session.getAttribute("loggedInMember")).getBskNo();
		// model.addAttribute("member", membVO.getMembNo());
		// model.addAttribute("basketList", basketService.getBasketList(memNo));
		model.addAttribute("bskNo", bskNo);
		model.addAttribute("membNo", memNo);
		model.addAttribute("prdtNo", prdtNo);
		List<BasketVO> list = new ArrayList<BasketVO>();
		list = basketService.getBasketList(memNo);

		System.out.println("장바구니 리스트 : " + list);
		model.addAttribute("basketList", list);

//		model.addAttribute("basketInfo", bskVO);

		// bskVO.setMembNo(mem.getMembNo());
		// bskVO.setMembNo(basketService.getIntPrdt(bskVO.getPrdtNo()));

//		model.addAttribute("basket", bskVO);
//		model.addAttribute("member", basketService.getMembInfo(bskVO.getMembNo()));
		// model.addAttribute("member", basketService.getIntPrdt(bskVO.getMembNo()));

//		if (mem != null) {
//			model.addAttribute("interestPrdt", basketService.getIntPrdt(mem.getMembNo()));
//		}

		return "mall/basketList";
	}

//	// 장바구니 추가 기능 이거야!!!!!!!!!!!!!
	@PostMapping("basketList")
	@ResponseBody
	public String addCartItem(BasketVO bskVO, HttpSession session) {
		System.out.println("~~~~~~~~~~~" + bskVO);
		UserVO mem = (UserVO) session.getAttribute("loggedInMember");

		if (mem == null) {
			return "fail"; // 로그인되지 않은 경우 처리
		}

		bskVO.setMembNo(mem.getMembNo());

		boolean result = basketService.addCartItem(bskVO);

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

//	// 장바구니 전체삭제
	@PostMapping("deleteAllBasket")
	@ResponseBody
	public String deleteAllBasket(BasketVO bskVO) {
		System.out.println(bskVO);
		if (basketService.deleteAllBasket(bskVO)) {
			return "success";
		} else {
			return "fail";
		}
	}

//	// 장바구니 단건삭제
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