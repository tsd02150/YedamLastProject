package com.yedam.app.mall.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.mall.mapper.MallMapper;
import com.yedam.app.mall.service.BasketVO;
import com.yedam.app.mall.service.MallService;

import com.yedam.app.mall.service.ProductReviewVO;
import com.yedam.app.mall.service.ProductVO;

@Service
public class MallServiceImpl implements MallService {
	
	@Autowired
	MallMapper mallMapper;

	//전체조회
	@Override
	public List<ProductVO> getProductList() {
		return mallMapper.getProductList();
	}
	
	//단건조회
	@Override
	public ProductVO getProductInfo(ProductVO prdtVO) {
		return mallMapper.getProductInfo(prdtVO);
	}
	
	//수정
	@Override
	public int updateReviewInfo(ProductReviewVO reviewVO) {
		return mallMapper.updateReviewInfo(reviewVO);
	}

	//삭제
	@Override
	public int deleteReviewInfo(int revNo) {
		return mallMapper.deleteReviewInfo(revNo);
	}

	//카테고리 정보
	@Override
	public List<ProductVO> getCtgr(String commonCd) {
		return mallMapper.getCtgr(commonCd);
	}

	
	//장바구니 리스트
	@Override
	public BasketVO getBasketInfo(BasketVO bskVO) {
		return mallMapper.getBasketInfo(bskVO);
	}

	@Override
	public List<BasketVO> getBasketList() {
		return mallMapper.getBasketList();
	}

	@Override
	public List<ProductReviewVO> getProductReviewList(ProductReviewVO revVO) {
		return mallMapper.getProductReviewList(revVO);
	}

	@Override
	public String getCategoryName(String commonCd) {
		return mallMapper.getCategoryName(commonCd);
	}

	

}
