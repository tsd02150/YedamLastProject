package com.yedam.app.mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import com.yedam.app.mall.service.BasketVO;
import com.yedam.app.mall.service.MallService;
import com.yedam.app.mall.service.ProductReviewVO;
import com.yedam.app.mall.service.ProductVO;

@Controller
@RequestMapping("mall")
public class MallController {

	@Autowired
	MallService mallService;

	// 포인트몰 메인 페이지
	@GetMapping("mallMain")
	public String mallMain(Model model) {
		List<ProductVO> list = mallService.getProductList();
		model.addAttribute("product", list);
		return "mall/mallMain";
	}
	
	@GetMapping("farmList")
	public String farmList(Model model, ProductVO prdtVO) {
	    List<ProductVO> farmList = mallService.getProductList();
	    String prdtCode = prdtVO.getCommonCd().substring(0, 2);
	    model.addAttribute("prdtCode", prdtCode);
	    System.out.println(prdtCode);
	    model.addAttribute("startPage", 1);
	    model.addAttribute("prdtName", mallService.getCategoryName(prdtVO.getCommonCd().substring(0, 2)));

	    model.addAttribute("farmList", farmList);

	    return "mall/farmList";
	}

	
	
	

	/*
	 * @GetMapping("farmList") public String farmList(Model model, ProductVO prdtVO)
	 * { List<ProductVO> farmList = mallService.getProductList(); String prdtCode =
	 * prdtVO.getCommonCd().substring(0, 2); model.addAttribute("prdtCode",
	 * prdtCode); model.addAttribute("startPage", 1); // 원하는 데이터만 가져오는 코드 추가
	 * 
	 * model.addAttribute("farmList", farmList);
	 * 
	 * 
	 * return "mall/farmList"; }
	 */

	/*
	 * // 농산물 페이지
	 * 
	 * @GetMapping("farmList") public String farmList(Model model, ProductVO prdtVO)
	 * { List<ProductVO> farmList = mallService.getProductList();
	 * model.addAttribute("prdtCode", prdtVO.getCommonCd().substring(0, 2));
	 * model.addAttribute("startPage",1); //model.addAttribute("prdtName",
	 * mallService.(prdtVO.getCommonCd().substring(0, 2)));
	 * 
	 * model.addAttribute("farmList", farmList); return "mall/farmList"; }
	 */
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
		model.addAttribute("prdtCode", prdtVO.getCommonCd().substring(0, 2));
		model.addAttribute("seafoodList", seafoodList);
		return "mall/seafoodList";
	}

	// 수산물 상세 페이지
	@GetMapping("getSeafood")
	public String seaFoodDetail(Model model, ProductVO prdtVO) {
		model.addAttribute("seaFood", mallService.getProductInfo(prdtVO));
		return "mall/getSeafood";
	}

	// 장바구니
	@GetMapping("basketList")
	public String baskeList(Model model, BasketVO bskVO) {
		model.addAttribute("getBasket", mallService.getBasketInfo(bskVO));
		return "mall/basketList";
	}

}
