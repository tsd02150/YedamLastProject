package com.yedam.app.member.mapper;

import com.yedam.app.member.service.AddrVO;
import com.yedam.app.member.service.MembVO;

public interface MemberMapper {
	
	//login - 회원정보 조회
	public MembVO loginCheck(MembVO membVO);
	
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
	
	

}
