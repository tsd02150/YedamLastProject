package com.yedam.app.mall.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.mall.mapper.BasketMapper;
import com.yedam.app.mall.service.BasketService;
import com.yedam.app.mall.service.BasketVO;
import com.yedam.app.member.service.MembVO;

@Service
public class BasketServiceImpl implements BasketService {

	@Autowired
	BasketMapper basketMapper;


	@Override
	public List<BasketVO> getBasketList(String membNo) {
		return basketMapper.getBasketList(membNo);
	}
	

	@Override
	public boolean addCartItem(BasketVO bskVO) {
		return basketMapper.addCartItem(bskVO)>0;
	}

	//전체삭제
	@Override
	public boolean deleteAllBasket(BasketVO bskVO) {
		return basketMapper.deleteAllBasket(bskVO)>0;
	}
	
	//단건 삭제
	@Override
	public boolean deleteBasket(String prdtNo) {
		return basketMapper.deleteBasket(prdtNo) >0;
	}

	@Override
	public MembVO getMembInfo(String membNo) {
		return basketMapper.getMembInfo(membNo);
	}

	@Override
	public String getIntPrdt(String prdtNo) {
		return basketMapper.getIntPrdt(prdtNo);
	}

	//장바구니 수량 업데이트
	@Override
	public boolean updateBasket(BasketVO bskVO) {
		return basketMapper.updateBasket(bskVO)>0;
	}

}
