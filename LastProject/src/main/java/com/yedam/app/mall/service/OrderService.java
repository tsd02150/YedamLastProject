package com.yedam.app.mall.service;

import java.util.List;

import com.yedam.app.member.service.MembVO;


public interface OrderService {
	
	public List<OrderVO> getOrderList(String membNo);
	public List<OrderVO> getMemInfo(String membNo);
	public int insertOrder(OrderVO ordVO, MembVO membVO); 
}
