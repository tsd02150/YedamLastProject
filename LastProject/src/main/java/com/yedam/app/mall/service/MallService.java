package com.yedam.app.mall.service;

import java.util.List;
import java.util.Optional;

public interface MallService {

	// 상품 전체조회
	public List<ProductVO> getProductList();

	// 상품 단건조회
	public ProductVO getProductInfo(ProductVO prdtVO);

	// 카테고리 정보
	public List<ProductVO> getCtgr(String commonCd);
	
	//카테고리 이름
	public String getCategoryName(String commonCd);
	
	// 장바구니 출력
	public List<BasketVO> getBasketList();

	// 장바구니 단건 가져오기
	public BasketVO getBasketInfo(BasketVO bskVO);

	// 해당 상품후기 리스트
	public List<ProductReviewVO> getProductReviewList(ProductReviewVO revVO);

	// 리뷰 수정
	public int updateReviewInfo(ProductReviewVO reviewVO);

	// 리뷰 삭제
	public int deleteReviewInfo(int revNo);



}
