package com.yedam.app.mall.service;

import java.util.List;

import com.yedam.app.member.service.AddrVO;
import com.yedam.app.member.service.MembVO;

public interface OrderService {

	public List<OrderVO> getOrderList(String membNo);

	public List<OrderVO> getMemInfo(String membNo);

	public int insertOrder(OrderVO ordVO, MembVO membVO);

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
