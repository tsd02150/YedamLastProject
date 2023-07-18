package com.yedam.app.mall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.yedam.app.mall.mapper.BasketMapper;
import com.yedam.app.mall.service.BasketService;
import com.yedam.app.mall.service.BasketVO;

@Service
public class BasketServiceImpl implements BasketService {
	
	@Autowired
	BasketMapper basketMapper;

	
	/*
	 * @Override public BasketVO getBasketInfo(BasketVO bskVO) { return
	 * basketMapper.getBasketInfo(bskVO); }
	 */

	@Override
	public boolean deleteAllBasket(BasketVO bskVO) {
		return basketMapper.deleteAllBasket(bskVO)>0;
	}

	@Override
	public List<BasketVO> getBasketList(Model userId) {
		return basketMapper.getBasketList(userId);
	}
	
}
