package com.yedam.app.mall.mapper;

import java.util.List;

import com.yedam.app.mall.service.ProductReviewVO;
import com.yedam.app.mall.service.ProductVO;

public interface MallMapper {
	
	//전체조회
	public List<ProductVO> getProductList();

	//단건조회
	public ProductVO getProductInfo(ProductVO prdtVO);
	//카테고리 정보
	public List<ProductVO> getCtgr(String commonCd);
	
	//상품후기
	
	//수정
	public int updateReviewInfo(ProductReviewVO reviewVO);
	
	//삭제
	public int deleteReviewInfo(int revNo);

	public ProductVO getProductInfo(String prdtNo);

}
