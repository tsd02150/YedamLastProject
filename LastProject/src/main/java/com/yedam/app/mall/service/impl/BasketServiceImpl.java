package com.yedam.app.mall.service.impl;

import java.util.List;

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
	public List<BasketVO> getBasketList() {
		return basketMapper.getBasketList();
	}

	@Override
	public BasketVO getBasketInfo(BasketVO bskVO) {
		return basketMapper.getBasketInfo(bskVO);
	}

	@Override
	public int deleteBasketInfo(int prdtNo) {
		return basketMapper.deleteBasketInfo(prdtNo);
	}
	
	
}
