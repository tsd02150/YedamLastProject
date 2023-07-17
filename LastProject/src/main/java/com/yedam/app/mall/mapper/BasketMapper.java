package com.yedam.app.mall.mapper;

import java.util.List;

import com.yedam.app.mall.service.BasketVO;

public interface BasketMapper {
	//장바구니 리스트
	public List<BasketVO> getBasketList();
	
	//장바구니 단건삭제
	public BasketVO getBasketInfo(BasketVO bskVO);
	
	//장바구니 전체 비우기
	public int deleteBasketInfo(int prdtNo);
	
	//선택 상품 결제
}
