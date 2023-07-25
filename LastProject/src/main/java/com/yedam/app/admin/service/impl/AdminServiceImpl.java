package com.yedam.app.admin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.admin.mapper.AdminMapper;
import com.yedam.app.admin.service.AdminService;
import com.yedam.app.admin.service.MembManageVO;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminMapper adminMapper;
	
	// 회원 리스트
	@Override
	public Map<String , Object> getMembList(int page, int perPage) {
		Map<String , Object> map = new HashMap<>();
		List<MembManageVO> list = adminMapper.getMembList(page, perPage);
		map.put("membList", list);
		map.put("membTotal", adminMapper.membCnt());
		return map;
	}
	
	// 회원정지
	@Override
	public int memberBan(String membNo, Integer period) {
		return adminMapper.memberBan(membNo, period);
	}

}
