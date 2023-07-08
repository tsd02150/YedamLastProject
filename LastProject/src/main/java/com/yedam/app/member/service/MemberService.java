package com.yedam.app.member.service;

public interface MemberService {
	//로그인
	public MembVO loginCheck(MembVO membVO);
	
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
	
	
	

}
