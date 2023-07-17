package com.yedam.app.mall.service;

import java.util.List;

public interface BasketService {
	
	//장바구니 리스트
	public List<BasketVO> getBasketList();
	
	//장바구니 단건삭제
	public BasketVO getBasketInfo(BasketVO bskVO);
	
	//장바구니 전체 비우기
	public int deleteBasketInfo(int prdtNo);
	
	//선택 상품 결제
}
