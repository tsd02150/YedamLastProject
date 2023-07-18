package com.yedam.app.mall.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.yedam.app.mall.service.ProductReviewVO;
import com.yedam.app.mall.service.ProductVO;

@Controller
@RequestMapping("mall")
public class MallController {

	@Autowired
	MallService mallService;
	
	@Autowired
	BasketService basketService;

	// 포인트몰 메인 페이지
	@GetMapping("mallMain")
	public String mallMain(Model model, ProductVO prdtVO) {
		List<ProductVO> list = mallService.getProductList();
		model.addAttribute("product", list);
		
		model.addAttribute("getFarm", mallService.getProductInfo(prdtVO));
		model.addAttribute("getSeafood", mallService.getProductInfo(prdtVO));
		return "mall/mallMain";
	}
	
	//농수산 페이지 전체
	@PostMapping("getCtgrList")
	@ResponseBody
	public List<ProductVO> getCtgrList(ProductVO prdtVO){
		return mallService.getCtgrList(prdtVO);
	}
	
	//농산물 페이지
	@GetMapping("farmList")
	public String farmList(Model model, ProductVO prdtVO) {
	    List<ProductVO> farmList = mallService.getProductList();
	    String prdtCode = prdtVO.getCommonCd().substring(0, 2);
	    model.addAttribute("prdtCode", prdtVO.getCommonCd().substring(0, 2));
	    System.out.println(prdtCode); // => S1 농산물, prdtVO.getCommonCd() => S10
	    model.addAttribute("S10", mallService.getCategoryName(prdtVO.getCommonCd().substring(0, 2)));
	    model.addAttribute("S101", mallService.getCategoryName(prdtVO.getCommonCd()+'1'+'0')); //곡류
	    model.addAttribute("S102", mallService.getCategoryName(prdtVO.getCommonCd()+'2'+'0')); //콩류
	    model.addAttribute("S103", mallService.getCategoryName(prdtVO.getCommonCd()+'3'+'0')); //과실류
	    //model.addAttribute("startPage", 1);
	    String cd = prdtVO.getCommonCd();
	    System.out.println(cd);
	    model.addAttribute("farmList", farmList);
	    
	    return "mall/farmList";
	}
	
	// 상품 게시물 갯수
	@PostMapping("getProductCount")
	@ResponseBody
	public int getProductCount(ProductVO prdtVO) {
		return mallService.getProductCount(prdtVO);
	}
	
	//농산물 상세 페이지
	@GetMapping("getFarm")
	public String getFarm(Model model, ProductVO prdtVO, ProductReviewVO revVO) {
		model.addAttribute("getFarm", mallService.getProductInfo(prdtVO));
		List<ProductReviewVO> review = mallService.getProductReviewList(revVO);
		// System.out.println(review);
		model.addAttribute("review", review);

		return "mall/getFarm";
	}

	// 수산물 페이지
	@GetMapping("seafoodList")
	public String seafoodList(Model model, ProductVO prdtVO) {
		List<ProductVO> seafoodList = mallService.getProductList();
	   // String prdtCode = prdtVO.getCommonCd().substring(0, 2);
	    model.addAttribute("prdtCode", prdtVO.getCommonCd());
	    
	    model.addAttribute("seafoodName", mallService.getCategoryName(prdtVO.getCommonCd()));
	    model.addAttribute("S201", mallService.getCategoryName(prdtVO.getCommonCd()+'1'+'0')); //어류
	    model.addAttribute("S202", mallService.getCategoryName(prdtVO.getCommonCd()+'2'+'0')); //해조류
	    model.addAttribute("S203", mallService.getCategoryName(prdtVO.getCommonCd()+'3'+'0')); //갑각류
		
	    model.addAttribute("startPage", 1);

	    model.addAttribute("seafoodList", seafoodList);

	    return "mall/seafoodList";
	}
	
	// 수산물 상세 페이지
	@GetMapping("getSeafood")
	public String getSeafood(Model model, ProductVO prdtVO, ProductReviewVO revVO) {
		model.addAttribute("getSeafood", mallService.getProductInfo(prdtVO));
		List<ProductReviewVO> review = mallService.getProductReviewList(revVO);
		// System.out.println(review);
		model.addAttribute("review", review);

		return "mall/getSeafood";
	}

	
}
