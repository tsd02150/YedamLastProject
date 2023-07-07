package com.yedam.app.member.mapper;

import com.yedam.app.member.service.impl.MembVO;

public interface MemberMapper {
	
	//login - 회원정보 조회
	public MembVO loginCheck(MembVO membVO);
	
	//닉네임 비교
	public int nickCheck(String nick);
	

}
