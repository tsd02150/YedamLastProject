package com.yedam.app.mall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.mall.mapper.MallMapper;
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
	

}
