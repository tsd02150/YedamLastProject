package com.yedam.app.mall.mapper;

import java.util.List;

import com.yedam.app.mall.service.OrderVO;

public interface OrderMapper {
	
	//주문 리스트
	public List<OrderVO> getOrderList(String membNo);
	public List<OrderVO> getMemInfo(String membNo);

}
