package com.yedam.app.mall.service;

import java.util.List;
import java.util.Optional;

public interface MallService {
	
	
	//상품 전체조회
	public List<ProductVO> getProductList();
	

	//상품 단건조회
	public ProductVO getProductInfo(ProductVO prdtVO);
	
	//해당 상품 
	
	//카테고리 정보
	public List<ProductVO> getCtgr(String commonCd);
	
	//상품후기
	
	//리뷰 수정
	public int updateReviewInfo(ProductReviewVO reviewVO);
	
	//리뷰 삭제
	public int deleteReviewInfo(int revNo);


}
