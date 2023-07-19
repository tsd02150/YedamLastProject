package com.yedam.app.member.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.mall.service.CouponVO;
import com.yedam.app.mall.service.OrderVO;
import com.yedam.app.mall.service.ProductVO;
import com.yedam.app.member.mapper.MemberMapper;
import com.yedam.app.member.service.AddrVO;
import com.yedam.app.member.service.BuyOrderVO;
import com.yedam.app.member.service.ChargeVO;
import com.yedam.app.member.service.DealVO;
import com.yedam.app.member.service.InterestVO;
import com.yedam.app.member.service.MembVO;
import com.yedam.app.member.service.MemberService;
import com.yedam.app.member.service.SellOrderVO;
import com.yedam.app.stock.service.StockVO;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	MemberMapper membMapper;

	//아이디로 단건조회
	@Override
	public MembVO selectOneMemb(String id) {
		return membMapper.selectOneMemb(id);
	}

	//닉네임 중복확인
	@Override
	public int nickCheckBoolean(String nick) {
		return membMapper.nickCheck(nick);
	}

	//아이디 중복확인
	@Override
	public int idCheckBoolean(String id) {
		return membMapper.idCheck(id);
	}

	@Override
	public int signUpMemb(MembVO membVO) {
		return membMapper.signUpMemb(membVO);
	}


	@Override
	public int insertAddr(AddrVO addrVO) {
		return membMapper.insertAddr(addrVO);
	}

	@Override
	public String getLastMembNo() {
		return membMapper.getLastMembNo();
	}

	@Override
	public String selectMembNO() {
		return membMapper.selectMembNO();
	}

	//비밀번호 변경
	@Override
	public int updatePwd(MembVO membVO) {
		return membMapper.updatePwd(membVO);
	}

	//관심종목 선택 리스트
	@Override
	public List<InterestVO> myItemCheck() {
		return membMapper.myItemCheck();
	}

	@Override
	public int updateTempPwd(MembVO membVO) {
		return membMapper.updateTempPwd(membVO);
	}

	// id 찾기 시 정보 조회
	@Override
	public MembVO findIdSelect(MembVO membVO) {
		return membMapper.findIdSelect(membVO);
	}

	@Override
	public int insertInterestItem(MembVO membVO) {
		return membMapper.insertInterestItem(membVO);
	}

	@Override
	public MembVO getMemberTel(MembVO membVO) {
		return membMapper.getMemberTel(membVO);
	}

	@Override
	public List<String> membListInfo(String id) {
		return membMapper.membListInfo(id);
	}
	
	@Override
	public List<MembVO> myStockList(MembVO membVO) {
		return membMapper.myStockList(membVO);
	}
	@Override
	public List<OrderVO> mypageOrderList(OrderVO orderVO) {
		return membMapper.mypageOrderList(orderVO);
	}
	@Override
	public List<ProductVO> mypagePrdtList(ProductVO prdtVO) {
		return membMapper.mypagePrdtList(prdtVO);
	}
	@Override
	public int updateMemberInfo(MembVO membVO) {
		return membMapper.updateMemberInfo(membVO);
	}
	@Override
	public int updateMemberAddr(AddrVO addrVO) {
		return membMapper.updateMemberAddr(addrVO);
	}

	@Override
	public List<MembVO> selectOneMemb2(MembVO membVO) {
		return membMapper.selectOneMemb2(membVO);
	}

	@Override
	public MembVO memberList(String id) {
		return membMapper.memberList(id);
	}

	@Override
	public List<CouponVO> mycoupon(String id) {
		return membMapper.mycoupon(id);
	}

	@Override
	public int insertCharge(ChargeVO chargeVO) {
		return membMapper.insertCharge(chargeVO);
	}

	@Override
	public List<StockVO> interestList(String membNo) {
		return membMapper.interestList(membNo);
	}

	@Override
	public List<SellOrderVO> sellOrderList(String membNo) {
		return membMapper.sellOrderList(membNo);
	}

	@Override
	public List<BuyOrderVO> buyOrderList(String membNo) {
		return membMapper.buyOrderList(membNo);
	}

	@Override
	public int deleteSellOrder(SellOrderVO soVO) {
		return membMapper.deleteSellOrder(soVO);
	}

	@Override
	public int deleteBuyOrder(BuyOrderVO boVO) {
		return membMapper.deleteBuyOrder(boVO);
	}

	@Override
	public int deleteInterest(String membNo, String itemNo) {
		return membMapper.deleteInterest(membNo, itemNo);
	}

	@Override
	public int updateSellOrder(SellOrderVO soVO) {
		return membMapper.updateSellOrder(soVO);
	}

	@Override
	public int updateBuyOrder(BuyOrderVO boVO) {
		return membMapper.updateBuyOrder(boVO);
	}

	@Override
	public List<DealVO> dealList(DealVO vo) {
		return membMapper.dealList(vo);
	}

}
