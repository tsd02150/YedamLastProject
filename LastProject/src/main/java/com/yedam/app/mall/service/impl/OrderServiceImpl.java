package com.yedam.app.mall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.mall.mapper.OrderMapper;
import com.yedam.app.mall.service.OrderService;
import com.yedam.app.mall.service.OrderVO;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	OrderMapper orderMapper;
	
	@Override
	public List<OrderVO> getOrderList(String membNo) {
		return orderMapper.getOrderList(membNo);
	}

	@Override
	public List<OrderVO> getMemInfo(String membNo) {
		return orderMapper.getMemInfo(membNo);
	}

	
}
