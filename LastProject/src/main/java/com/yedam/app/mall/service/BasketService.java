package com.yedam.app.mall.service;

import java.util.List;

import com.yedam.app.member.service.MembVO;


public interface BasketService {
	

//	/* 카트 추가 */
//	public int addBasket(BasketVO bskVO);
//	
//	/* 카트 삭제 */
//	public int deleteBasket(String bskNo);
//	
//	/* 카트 수량 수정 */
//	public int modifyCount(BasketVO bskVO);
//	
//	/* 카트 목록 */
//	public List<BasketVO> getBasket(String membNo);	
//	
//	/* 카트 확인 */
//	public BasketVO checkBasket(BasketVO bskVO);
	
	
	
	

//	// 장바구니 리스트
	public List<BasketVO> getBasketList(String membNo);

//	// 장바구니 단건삭제
	public boolean deleteBasket(String prdtNo);

//	// 장바구니 전체 삭제
	public boolean deleteAllBasket(BasketVO bskVO);
	
//	//장바구니 추가 회원 정보
	public MembVO getMembInfo(String membNo);
	
//	// 장바구니 아이템번호
	public String getIntPrdt(String prdtNo);

//	// 장바구니 추가
	public boolean addCartItem(BasketVO bskVO);
}
