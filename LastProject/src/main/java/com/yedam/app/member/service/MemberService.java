package com.yedam.app.member.service;

import java.util.List;

import com.yedam.app.mall.service.CouponVO;
import com.yedam.app.mall.service.OrderVO;
import com.yedam.app.mall.service.ProductVO;
import com.yedam.app.security.service.UserVO;

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
	public List<MembVO> myinterestList(MembVO membVO);
	
	//보유주식
	public List<MembVO> myStockList(MembVO membVO);
	
	public List<OrderVO> mypageOrderList(OrderVO orderVO);
	
	public List<ProductVO> mypagePrdtList(ProductVO prdtVO);
	
	//회원정보 변경
	public int updateMemberInfo(MembVO membVO);
	public int updateMemberAddr(AddrVO addrVO);

	//보유 쿠폰
	public List<CouponVO> mycoupon(String id);
}
