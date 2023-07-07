package com.yedam.app.member.service.impl;

public interface MemberService {

	//로그인
	public MembVO loginCheck(MembVO membVO);
	
	//회원가입 - 닉네임 중복확인
	public int nickCheckBoolean(String nick);

}
