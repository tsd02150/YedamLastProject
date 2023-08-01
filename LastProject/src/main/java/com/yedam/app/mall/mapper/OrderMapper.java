package com.yedam.app.mall.mapper;

import java.util.List;

import com.yedam.app.mall.service.OrderDetailVO;
import com.yedam.app.mall.service.OrderVO;
import com.yedam.app.mall.service.ShippingVO;
import com.yedam.app.member.service.AddrVO;
import com.yedam.app.member.service.MembVO;

public interface OrderMapper {

	// 주문 리스트
	public List<OrderVO> getOrderList(String membNo);
	public List<OrderVO> getMemInfo(String membNo);
	// 주소 등록
	public int insertAddr(AddrVO addrVO);
	public MembVO memberList(String id);
	public List<String> membListInfo(String id);
	public MembVO selectOneMemb(String id);
	public List<MembVO> selectOneMemb2(MembVO membVO);
	// 주문번호
	public String getOrderNo();
	// 주문 상세번호
	public String getOrderDetaNo();
	// 주문 정보 추가
	public int insertOrderInfo(OrderVO ordVO);
	// 주문 상세 정보 추가
	public int insertOrderDetaInfo(OrderVO oddVO);
	// 회원 정보변경
	public int updateMemberInfo(MembVO membVO);
	// 주소 변경
	public int updateMemberAddr(AddrVO addrVO);
	//배송 추가
	public int insertShipping(OrderVO ordVO);
	
}
