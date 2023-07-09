package com.yedam.app.member.mapper;

import java.util.List;

import com.yedam.app.member.service.AddrVO;
import com.yedam.app.member.service.CommonVO;
import com.yedam.app.member.service.MembVO;

public interface MemberMapper {
	
	//login - 회원정보 조회
	public MembVO loginCheck(MembVO membVO);
	
	//단건조회
	public MembVO selectOneMemb(String id);
	
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
	
	//관심종목 선택(회원가입 시)
	public List<CommonVO> myItemCheck(); // 데이터 쌓이면 상승률 기준 list로 바꿔야함
	

}
