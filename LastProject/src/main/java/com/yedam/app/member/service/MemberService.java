package com.yedam.app.member.service;

import java.util.List;

public interface MemberService {
	//로그인
	public MembVO loginCheck(MembVO membVO);
	public MembVO loginCheckPwd(MembVO membVO); //임시비밀번호 발급 후
	
	//아이디로 단건조회
	public MembVO selectOneMemb(String id);
	
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
	public List<CommonVO> myItemCheck();
	

}
