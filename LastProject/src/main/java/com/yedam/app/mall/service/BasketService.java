package com.yedam.app.mall.service;

import java.util.List;


public interface BasketService {

	// 장바구니 리스트
	public List<BasketVO> getBasketList(BasketVO bskVO);

	// 장바구니 단건삭제
	public boolean deleteBasket(String prdtNo);

	// 장바구니 전체 삭제
	public boolean deleteAllBasket(BasketVO bskVO);


	// 장바구니 아이템
	public List<BasketVO> getIntPrdt(String membNo);

	// 장바구니 추가
	public boolean addCartItem(BasketVO bskVO);
}
