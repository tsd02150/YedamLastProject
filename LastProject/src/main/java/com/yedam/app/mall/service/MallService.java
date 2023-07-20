package com.yedam.app.mall.service;

import java.util.List;
import java.util.Optional;

import com.yedam.app.common.service.CommonCodeVO;

public interface MallService {

	// 상품 전체조회
	public List<ProductVO> getProductList(String cd);

	// 상품 단건조회
	public ProductVO getProductInfo(ProductVO prdtVO);
	
	// 상품 게시물 갯수
	public int getProductCount(ProductVO prdtVO);

	// 카테고리 정보
	public List<ProductVO> getCtgrList(ProductVO prdtVO);

	// 카테고리별 상품 조회
	public List<ProductVO> getProductListByCategory(String categoryCode);

	// 카테고리 이름
	public List<CommonCodeVO> getCategoryName(String commonCd);

	// 해당 상품후기 리스트
	public List<ProductReviewVO> getProductReviewList(ProductReviewVO revVO);
	
	// 리뷰 등록
	public boolean addReview(ProductReviewVO revVO);
	
	// 리뷰 수정
	public int updateReviewInfo(ProductReviewVO revVO);

	// 리뷰 삭제
	public boolean deleteReviewInfo(ProductReviewVO revVO);

}
