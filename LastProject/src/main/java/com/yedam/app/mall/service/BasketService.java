package com.yedam.app.mall.service;

import java.util.List;

import com.yedam.app.member.service.MembVO;


public interface BasketService {
	
	// 장바구니 리스트
	public List<BasketVO> getBasketList(String membNo);
	
	// 장바구니 정보리스트
	public List<BasketVO> getBasketInfoList(BasketVO bskVO);

	// 장바구니 단건삭제
	public boolean deleteBasket(String bskNo);

	// 장바구니 전체 삭제
	public boolean deleteAllBasket(BasketVO bskVO);
	
	//장바구니 추가 회원 정보
	public MembVO getMembInfo(String membNo);
	
	// 장바구니 아이템번호
	public String getIntPrdt(String prdtNo);

	// 장바구니 추가
	public boolean addCartItem(BasketVO bskVO);

	// 장바구니 수량 업데이트
	public boolean updateCnt(BasketVO bskVO);

}
