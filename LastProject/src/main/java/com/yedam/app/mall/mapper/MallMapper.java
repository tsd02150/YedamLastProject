package com.yedam.app.mall.mapper;

import java.util.List;

import com.yedam.app.mall.service.BasketVO;
import com.yedam.app.mall.service.ProductReviewVO;
import com.yedam.app.mall.service.ProductVO;

public interface MallMapper {
	
	//전체조회
	public List<ProductVO> getProductList();

	//단건조회
	public ProductVO getProductInfo(ProductVO prdtVO);
	//카테고리 정보
	public List<ProductVO> getCtgr(String commonCd);
	
	//카테고리 이름
	public String getCategoryName(String commonCd);
	
	//장바구니 출력
	public List<BasketVO> getBasketList();
	
	//장바구니 정보
	public BasketVO getBasketInfo(BasketVO bskVO);
	
	// 해당 상품후기 리스트
	/* public List<ProductReviewVO> getProductReviewList(ProductReviewVO revVO); */
	public List<ProductReviewVO> getProductReviewList(ProductReviewVO revVO);
	
	//수정
	public int updateReviewInfo(ProductReviewVO reviewVO);
	
	//삭제
	public int deleteReviewInfo(int revNo);

	public ProductVO getProductInfo(String prdtNo);

}
