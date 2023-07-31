package com.yedam.app.mall.service;

import java.util.List;

import com.yedam.app.member.service.AddrVO;
import com.yedam.app.member.service.MembVO;

public interface OrderService {

	public List<OrderVO> getOrderList(String membNo);

	public List<OrderVO> getMemInfo(String membNo);
	
	//주문 등록
	public int insertOrder(OrderVO ordVO, MembVO membVO);
	
	// 배송 등록
	public int insertShipping(ShippingVO shipVO, MembVO membVO);
	
	//주문 상세 등록
	public int insertOrderDetail(OrderDetailVO oddVO, MembVO membVO);

	// 주소 등록
	public int insertAddr(AddrVO addrVO);
	public List<String> membListInfo(String id);
	public MembVO memberList(String id); // member table만 조회

	public MembVO selectOneMemb(String id);
	public List<MembVO> selectOneMemb2(MembVO membVO);
	
	public int updateMemberInfo(MembVO membVO);
	// 주소 변경
	public int updateMemberAddr(AddrVO addrVO);
	
	

}
