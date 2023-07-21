package com.yedam.app.mall.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.mall.mapper.BasketMapper;
import com.yedam.app.mall.service.BasketService;
import com.yedam.app.mall.service.BasketVO;

@Service
public class BasketServiceImpl implements BasketService {
	
	@Autowired
	BasketMapper basketMapper;

	@Override
	public List<BasketVO> getBasketList(BasketVO bskVO) {
		return basketMapper.getBasketList(bskVO);
	}
	
	@Override
	public List<BasketVO> getIntPrdt(String membNo) {
		// TODO Auto-generated method stub
		return null;
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


}
