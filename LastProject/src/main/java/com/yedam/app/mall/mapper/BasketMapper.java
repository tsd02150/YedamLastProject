package com.yedam.app.mall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yedam.app.mall.service.BasketVO;

public interface BasketMapper {
	
	//장바구니 리스트
	public List<BasketVO> getBasketList(BasketVO bskVO);
	
	//장바구니 단건삭제
	public int deleteBasket(String prdtNo);
	
	//장바구니 전체 비우기
	public int deleteAllBasket(BasketVO bskVO);

	//장바구니 추가
	public int addCartItem(BasketVO bskVO);

	// 장바구니 중복체크
	public String itemCheck(@Param("membNo")String membNo, @Param("prdtNo")String prdtNo);
}
