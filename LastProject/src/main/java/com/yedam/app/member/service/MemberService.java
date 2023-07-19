package com.yedam.app.member.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yedam.app.mall.service.CouponVO;
import com.yedam.app.mall.service.OrderVO;
import com.yedam.app.mall.service.ProductVO;
import com.yedam.app.stock.service.StockVO;

public interface MemberService {
	
	public List<String> membListInfo(String id);
	public MembVO memberList(String id); //member table만 조회
	public MembVO selectOneMemb(String id);
	public List<MembVO> selectOneMemb2(MembVO membVO);
	
	public MembVO getMemberTel(MembVO membVO);
	
	//일반회원가입
	public int signUpMemb(MembVO membVO);
	
	//회원가입 - 닉네임 중복확인
	public int nickCheckBoolean(String nick);
	
	//회원가입 - id 중복확인
	public int idCheckBoolean(String id);
	
	//주소추가
	public int insertAddr(AddrVO addrVO);

	public String getLastMembNo();  
	
	public String selectMembNO();
	
	//비밀번호 변경
	public int updatePwd(MembVO membVO);
	public int updateTempPwd(MembVO membVO);
	
	//관심종목 선택 리스트
	public List<InterestVO> myItemCheck();
	
	// 아이디 찾기 - 이름, 연락처 비교
	public MembVO findIdSelect(MembVO membVO);
	
	//관심종목 선택
	public int insertInterestItem(MembVO membVO);
	
	//마이페이지
	
	//관심종목
	public List<StockVO> interestList(String membNo);
	
	//보유주식
	public List<MembVO> myStockList(MembVO membVO);
	
	public List<OrderVO> mypageOrderList(OrderVO orderVO);
	
	public List<ProductVO> mypagePrdtList(ProductVO prdtVO);
	
	//회원정보 변경
	public int updateMemberInfo(MembVO membVO);
	public int updateMemberAddr(AddrVO addrVO);

	//보유 쿠폰
	public List<CouponVO> mycoupon(String id);
	
	//포인트 충전
	public int insertCharge(ChargeVO chargeVO);
	
	//매도 주문 현황 리스트
	public List<SellOrderVO> sellOrderList(String membNo);
	//매수 주문 현황 리스트
	public List<BuyOrderVO> buyOrderList(String membNo);
	//매도 주문 현황 삭제
	public int deleteSellOrder(SellOrderVO soVO);
	//매수 주문 현황 삭제
	public int deleteBuyOrder(BuyOrderVO boVO);
	//매도 주문 현황 삭제
	public int updateSellOrder(SellOrderVO soVO);
	//매수 주문 현황 수정
	public int updateBuyOrder(BuyOrderVO boVO);
	//관심종목 삭제
	public int deleteInterest(@Param("membNo")String membNo , @Param("itemNo")String itemNo);
	//거래내역 조회
	public List<DealVO> dealList(DealVO vo);
}
