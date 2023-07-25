package com.yedam.app.mall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.common.service.CommonCodeVO;
import com.yedam.app.mall.mapper.MallMapper;
import com.yedam.app.mall.service.MallService;
import com.yedam.app.mall.service.ProductReviewVO;
import com.yedam.app.mall.service.ProductVO;

@Service
public class MallServiceImpl implements MallService {

	@Autowired
	MallMapper mallMapper;

	// 전체조회
	@Override
	public List<ProductVO> getProductList(String cd) {
		return mallMapper.getProductList(cd);
	}

	// 단건조회
	@Override
	public ProductVO getProductInfo(ProductVO prdtVO) {
		return mallMapper.getProductInfo(prdtVO);
	}

	// 등록
	@Override
	public boolean addReview(ProductReviewVO revVO) {
		return mallMapper.addReview(revVO) > 0;
	}

	// 수정
	@Override
	public int updateReviewInfo(ProductReviewVO revVO) {
		return mallMapper.updateReviewInfo(revVO);
	}
	
	// 삭제
	@Override
	public boolean deleteReviewInfo(String revNo) {
		return mallMapper.deleteReviewInfo(revNo) > 0;
	}

	// 카테고리 정보
	@Override
	public List<ProductVO> getCtgrList(ProductVO prdtVO) {
		return mallMapper.getCtgrList(prdtVO);
	}

	@Override
	public List<ProductReviewVO> getProductReviewList(ProductReviewVO revVO) {
		return mallMapper.getProductReviewList(revVO);
	}

	@Override
	public List<CommonCodeVO> getCategoryName(String commonCd) {
		return mallMapper.getCategoryName(commonCd);
	}

	@Override
	public List<ProductVO> getProductListByCategory(String commonCd) {
		return mallMapper.getProductListByCategory(commonCd);
	}

	@Override
	public int getProductCount(ProductVO prdtVO) {
		return mallMapper.getProductCount(prdtVO);
	}

	@Override
	public List<ProductVO> getLowPrc(ProductVO prdtVO) {
		return mallMapper.getLowPrc(prdtVO);
	}

	@Override
	public List<ProductVO> getHighPrc(ProductVO prdtVO) {
		return mallMapper.getHighPrc(prdtVO);
	}

	@Override
	public List<ProductVO> getSearchPrdt(ProductVO prdtVO) {
		return mallMapper.getSearchPrdt(prdtVO);
	}
	
}
	