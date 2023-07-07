package com.yedam.app.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.member.mapper.MemberMapper;
import com.yedam.app.member.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	MemberMapper membMapper;

	//로그인
	@Override
	public MembVO loginCheck(MembVO membVO) {
		return membMapper.loginCheck(membVO);
	}

	//닉네임 중복확인
	@Override
	public int nickCheckBoolean(String nick) {
		return membMapper.nickCheck(nick);
	}

	//아이디 중복확인
	@Override
	public int idCheckBoolean(String id) {
		return membMapper.idCheck(id);
	}

}
