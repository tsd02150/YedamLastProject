package com.yedam.app.mall.mapper;

import java.util.List;

import com.yedam.app.common.service.CommonCodeVO;
import com.yedam.app.mall.service.BasketVO;
import com.yedam.app.mall.service.ProductReviewVO;
import com.yedam.app.mall.service.ProductVO;

public interface MallMapper {

	// 전체조회
	public List<ProductVO> getProductList(String cd);

	// 단건조회
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
	/* public List<ProductReviewVO> getProductReviewList(ProductReviewVO revVO); */
	public List<ProductReviewVO> getProductReviewList(ProductReviewVO revVO);
	
	// 리뷰등록
	public int addReview(ProductReviewVO revVO);
	
	// 수정
	public int updateReviewInfo(ProductReviewVO revVO);

	// 리뷰삭제
	public int deleteReviewInfo(ProductReviewVO revVO);

	public ProductVO getProductInfo(String prdtNo);

}
