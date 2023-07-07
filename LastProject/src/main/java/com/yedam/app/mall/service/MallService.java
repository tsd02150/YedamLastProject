package com.yedam.app.mall.service;

import java.util.List;

public interface MallService {
	
	
	//전체조회
	public List<ProductVO> getProductList();

	//단건조회
	public ProductVO getProductInfo(ProductVO prdtVO);

	
	//상품후기
	
	//수정
	public int updateReviewInfo(ProductReviewVO reviewVO);
	
	//삭제
	public int deleteReviewInfo(int revNo);


}
