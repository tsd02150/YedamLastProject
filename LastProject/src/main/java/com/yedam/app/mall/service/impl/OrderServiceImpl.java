package com.yedam.app.mall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.mall.mapper.OrderMapper;
import com.yedam.app.mall.service.OrderDetailVO;
import com.yedam.app.mall.service.OrderService;
import com.yedam.app.mall.service.OrderVO;
import com.yedam.app.mall.service.ShippingVO;
import com.yedam.app.member.service.AddrVO;
import com.yedam.app.member.service.MembVO;

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

	@Override
	public int insertAddr(AddrVO addrVO) {
		return orderMapper.insertAddr(addrVO);
	}

	@Override
	public MembVO memberList(String id) {
		return orderMapper.memberList(id);
	}

	@Override
	public MembVO selectOneMemb(String id) {
		return orderMapper.selectOneMemb(id);
	}

	@Override
	public int updateMemberAddr(AddrVO addrVO) {
		return orderMapper.updateMemberAddr(addrVO);
	}

	@Override
	public List<MembVO> selectOneMemb2(MembVO membVO) {
		return orderMapper.selectOneMemb2(membVO);
	}

	@Override
	public List<String> membListInfo(String id) {
		return orderMapper.membListInfo(id);
	}

	@Override
	public int updateMemberInfo(MembVO membVO) {
		return orderMapper.updateMemberInfo(membVO);
	}


	@Override
	public int insertShipping(OrderVO ordVO) {
		return orderMapper.insertShipping(ordVO);
	}


	@Override
	public String getOrderNo() {
		return orderMapper.getOrderNo();
	}

	@Override
	public String getOrderDetaNo() {
		return orderMapper.getOrderDetaNo();
	}

	@Override
	public boolean insertOrderInfo(OrderVO ordVO) {
		return orderMapper.insertOrderInfo(ordVO)>0;
	}

	@Override
	public boolean insertOrderDetaInfo(OrderVO oddVO) {
		return orderMapper.insertOrderDetaInfo(oddVO)>0;
	}

	
}
