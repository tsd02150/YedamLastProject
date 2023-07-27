package com.yedam.app.mall.service;

import java.util.List;


public interface OrderService {
	
	public List<OrderVO> getOrderList(String membNo);
	public List<OrderVO> getMemInfo(String membNo);
}
