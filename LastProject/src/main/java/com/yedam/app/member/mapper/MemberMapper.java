package com.yedam.app.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yedam.app.mall.service.CouponVO;
import com.yedam.app.mall.service.OrderVO;
import com.yedam.app.mall.service.ProductVO;
import com.yedam.app.mall.service.ShippingVO;
import com.yedam.app.member.service.AddrVO;
import com.yedam.app.member.service.BuyOrderVO;
import com.yedam.app.member.service.ChargeVO;
import com.yedam.app.member.service.DealVO;
import com.yedam.app.member.service.InterestVO;
import com.yedam.app.member.service.MembVO;
import com.yedam.app.member.service.PossVO;
import com.yedam.app.member.service.SellOrderVO;
import com.yedam.app.member.service.SurveyVO;
import com.yedam.app.stock.service.StockVO;

public interface MemberMapper {

	
	//login - 회원정보 조회
	public List<String> membListInfo(String id);
	
	//role 체크
	public List<String> selectRole(String id);
	
	//단건조회
	public MembVO selectOneMemb(String id);
	public List<MembVO> selectOneMemb2(MembVO membVO);
	public MembVO memberList(String id); //member 테이블만
	
	//member 정보 조회
	public MembVO getMemberTel(MembVO membVO);
	
	//닉네임 중복확인
	public int nickCheck(String nick);
	
	//id 중복확인
	public int idCheck(String id);
	
	//회원가입 - member table
	public int signUpMemb(MembVO membVO);
	
	//주소 추가
	public int insertAddr(AddrVO addrVO);  
	
	public String getLastMembNo();
	
	public String selectMembNO();
	
	//비밀번호 변경
	public int updatePwd(MembVO membVO);
	public int updateTempPwd(MembVO membVO);
	
	//관심종목 선택(회원가입 시)
	public List<InterestVO> myItemCheck(); // 데이터 쌓이면 상승률 기준 list로 바꿔야함
	
	// 아이디 찾기 - 이름, 연락처 비교 & 이름, 연락처, id 조회
	public MembVO findIdSelect(MembVO membVO);
	
	//관심종목 추가
	public int insertInterestItem(MembVO membVO);
	
	//관심종목
	public List<StockVO> interestList(String membNo);
	
	//보유주식
	public List<StockVO> myStockList(String membNo);
	
	//배송, 주문 리스트
	public List<OrderVO> mypageOrderList(OrderVO orderVO);
	
	//상품 이름, 이미지
	public List<ProductVO> mypagePrdtList(ProductVO prdtVO);
	
	//회원정보 수정
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
	//매도 주문 현황 수정
	public int updateSellOrder(SellOrderVO soVO);
	//매도 주문 수정 후 보유주식 수정
	public int updatePoss(PossVO vo);
	//매수 주문 현황 수정
	public int updateBuyOrder(BuyOrderVO boVO);
	
	//관심종목 삭제
	public int deleteInterest(@Param("membNo")String membNo , @Param("itemNo")String itemNo);
	
	//거래내역 조회
	public List<DealVO> dealList(DealVO vo);
	
	//거래내역 개수
	public int getDealCount(DealVO vo);
	
	//보유자산
	public List<PossVO> myPossStockList(String membNo);
	
	//매도 매수 거래내역
	public List<DealVO> buysellList(DealVO vo);
	//매도 매수 거래내역 개수
	public int buysellCount(DealVO vo);
	
	//설문조사 추가
	public int insertsurvey(SurveyVO vo);
	//설문조사 결과 조회
	public List<SurveyVO> analysisResult(String membNo);
	
	//하루 총 수익(매수, 매도)
	public List<PossVO> myRaiseList(String membNo);
	
	//익명 memb_no 'ano-'
	public String anoSelectKey();
	
	//추천순위
	public List<PossVO> recomList(String membNo);
	
	//회원탈퇴
	public int deleteMemb(String membNo);
	//회원 백업
	public int insertbackup(MembVO membVO);
	//주문.배송 내역
	public List<ShippingVO> shipList(ShippingVO vo);
	public int getShipListCount(ShippingVO vo);
	//주문상세내역
	public List<ShippingVO> myorderDetaList(ShippingVO vo);
	//주문취소
	public int deleteOrder(OrderVO vo);
	public int updateShip(ShippingVO vo);
	public String selectOrderOne(OrderVO vo);

}
