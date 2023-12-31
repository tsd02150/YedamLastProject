package com.yedam.app.mall.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.common.service.CommonCodeVO;
import com.yedam.app.mall.service.BasketService;
import com.yedam.app.mall.service.BasketVO;
import com.yedam.app.mall.service.CouponVO;
import com.yedam.app.mall.service.MallService;
import com.yedam.app.mall.service.ProductReviewVO;
import com.yedam.app.mall.service.ProductVO;
import com.yedam.app.member.service.MembVO;
import com.yedam.app.member.service.MemberService;
import com.yedam.app.security.service.UserVO;

//신지은 , 포인트 몰 
@Controller
@RequestMapping("mall")
public class MallController {

	@Autowired
	MallService mallService;

	@Autowired
	BasketService basketService;

	@Autowired
	MemberService membService;

	// 포인트몰 메인 페이지
	@GetMapping("mallMain")
	public String mallMain(Model model, ProductVO prdtVO) {
		List<ProductVO> farmList = mallService.getProductList("S1");
		List<ProductVO> seaList = mallService.getProductList("S2");
		model.addAttribute("farmProduct", farmList);
		model.addAttribute("seaProduct", seaList);

		return "mall/mallMain";
	}

	// 농수산 페이지 전체
	@PostMapping("getCtgrList")
	@ResponseBody
	public List<ProductVO> getCtgrList(ProductVO prdtVO) {
		return mallService.getCtgrList(prdtVO);
	}

	// 농/수산물 페이지
	@GetMapping("productList")
	public String farmList(Model model, ProductVO prdtVO, HttpSession session, MembVO membVO) {

		String prdtCode = prdtVO.getCommonCd();

		// 카테고리 분류
		model.addAttribute("prdtCode", prdtVO.getCommonCd().substring(0, 2)); // S1, S2
		List<CommonCodeVO> list = mallService.getCategoryName(prdtCode);
		model.addAttribute("S", list);
		for (int i = 0; i < list.size(); i++) {
			String cd = list.get(i).getCommonCd();
			list.get(i).setChildList(mallService.getCategoryName(list.get(i).getCommonCd())); // 소분류 카테고리
		}
		return "mall/productList";
	}

	// 검색
	@PostMapping("getSearchPrdt")
	@ResponseBody
	public List<ProductVO> getSearchPrdt(ProductVO prdtVO) {
		return mallService.getSearchPrdt(prdtVO);
	}

	// 낮은 가격순 정렬 버튼
	@PostMapping("getLowPrc")
	@ResponseBody
	public List<ProductVO> getLowPrc(ProductVO prdtVO) {
		return mallService.getLowPrc(prdtVO);

	}

	// 높은 가격순 정렬 버튼
	@PostMapping("getHighPrc")
	@ResponseBody
	public List<ProductVO> getHighPrc(ProductVO prdtVO) {
		return mallService.getHighPrc(prdtVO);

	}

	// 상품 게시물 갯수
	@PostMapping("getProductCount")
	@ResponseBody
	public int getProductCount(ProductVO prdtVO) {
		return mallService.getProductCount(prdtVO);
	}

	// 농산물 상세 페이지
	@GetMapping("getFarm")
	public String getFarm(Model model, ProductVO prdtVO, ProductReviewVO revVO, BasketVO bskVO, HttpSession session) {

		// 첫 화면 페이징 1
		model.addAttribute("startPage", 1);
		model.addAttribute("getFarm", mallService.getProductInfo(prdtVO));

		List<ProductVO> list = mallService.getProductList(null);
		model.addAttribute("info", list);
		model.addAttribute("reviewInfo", revVO);
		String membNo = "";
		if (session.getAttribute("loggedInMember") != null) {
			membNo = ((UserVO) session.getAttribute("loggedInMember")).getMembNo();
		}

		model.addAttribute("membNo", membNo);
		return "mall/getFarm";
	}

	// 리뷰 정보 갖고오기
	@PostMapping("getReviewList")
	@ResponseBody
	public List<ProductReviewVO> getReviewList(ProductReviewVO revVO) {

		List<ProductReviewVO> review = mallService.getProductReviewList(revVO);

		return review;
	}

	// 리뷰등록
	@PostMapping("addReview")
	@ResponseBody
	public ProductReviewVO addReview(HttpSession session, ProductReviewVO revVO, Model model) {
		UserVO mem = (UserVO) session.getAttribute("loggedInMember");
		revVO.setMembNo(mem.getMembNo());
		boolean result = mallService.addReview(revVO);
		if (result) {
			revVO = mallService.getProductReview(revVO.getRevNo());
			return revVO;
		} else {
			return null;
		}
	}

	// 리뷰삭제
	@PostMapping("deleteReview")
	@ResponseBody
	public String deleteReview(ProductReviewVO revVO, HttpSession session) {
		UserVO mem = (UserVO) session.getAttribute("loggedInMember");
		revVO.setMembNo(mem.getMembNo());
		if (mallService.deleteReviewInfo(revVO)) {
			return "success";
		} else {
			return "fail";
		}
	}

}
