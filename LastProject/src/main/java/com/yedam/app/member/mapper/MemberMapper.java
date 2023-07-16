package com.yedam.app.member.mapper;

import java.util.List;

import com.yedam.app.mall.service.OrderVO;
import com.yedam.app.mall.service.ProductVO;
import com.yedam.app.member.service.AddrVO;
import com.yedam.app.member.service.InterestVO;
import com.yedam.app.member.service.MembVO;
import com.yedam.app.security.service.UserVO;

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
	public List<MembVO> myinterestList(MembVO membVO);
	
	//보유주식
	public List<MembVO> myStockList(MembVO membVO);
	
	//배송, 주문 리스트
	public List<OrderVO> mypageOrderList(OrderVO orderVO);
	
	//상품 이름, 이미지
	public List<ProductVO> mypagePrdtList(ProductVO prdtVO);
	
	//회원정보 수정
	public int updateMemberInfo(MembVO membVO);
	public int updateMemberAddr(AddrVO addrVO);

}
