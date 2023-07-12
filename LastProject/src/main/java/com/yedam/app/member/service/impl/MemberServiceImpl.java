package com.yedam.app.member.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.member.mapper.MemberMapper;
import com.yedam.app.member.service.AddrVO;
import com.yedam.app.member.service.InterestVO;
import com.yedam.app.member.service.MembVO;
import com.yedam.app.member.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	MemberMapper membMapper;

	//로그인
//	@Override
//	public MembVO loginCheck(MembVO membVO) {
//		return membMapper.loginCheck(membVO);
//	}
	
	//임시 비밀번호 발급 후 로그인
//	@Override
//	public MembVO loginCheckPwd(MembVO membVO) {
//		return membMapper.loginCheckPwd(membVO);
//	}
//	
	//아이디로 단건조회
	@Override
	public MembVO selectOneMemb(String id) {
		return membMapper.selectOneMemb(id);
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

	@Override
	public int signUpMemb(MembVO membVO) {
		return membMapper.signUpMemb(membVO);
	}


	@Override
	public int insertAddr(AddrVO addrVO) {
		return membMapper.insertAddr(addrVO);
	}

	@Override
	public String getLastMembNo() {
		return membMapper.getLastMembNo();
	}

	@Override
	public String selectMembNO() {
		return membMapper.selectMembNO();
	}

	//비밀번호 변경
	@Override
	public int updatePwd(MembVO membVO) {
		return membMapper.updatePwd(membVO);
	}

	//관심종목 선택 리스트
	@Override
	public List<InterestVO> myItemCheck() {
		return membMapper.myItemCheck();
	}

	@Override
	public int updateTempPwd(MembVO membVO) {
		return membMapper.updateTempPwd(membVO);
	}

	// id 찾기 시 정보 조회
	@Override
	public List<MembVO> findIdSelect(MembVO membVO) {
		return membMapper.findIdSelect(membVO);
	}

	@Override
	public int insertInterestItem(MembVO membVO) {
		return membMapper.insertInterestItem(membVO);
	}

	@Override
	public MembVO loginCheckInfo(MembVO membVO) {
		return membMapper.loginCheckInfo(membVO);
	}

	@Override
	public MembVO getMember(MembVO membVO) {
		return membMapper.getMember(membVO);
	}



}
