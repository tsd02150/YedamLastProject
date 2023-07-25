package com.yedam.app.admin.service.impl;

import java.util.ArrayList;
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
	public int memberBan(List<String> list, Integer period) {
		int cnt = list.size();
		int result =0;
		List<String> being = new ArrayList<>();
		List<String> banList = adminMapper.bannedMemb();
		//			adminMapper.memberBan(str, period);

		//adminMapper.addBanPeriod(str, period);
		// 이미 정지 중인 사람들은 뽑아내고 새로 정지되는 사람은 정지
		for(String str : list) {
			int addcnt = 0;
			for(String str2 : banList) {
				if(str == str2) {
					addcnt++;
					being.add(str);
				}
			}
			if(addcnt > 0) {
				continue;
			}else {
				result += adminMapper.memberBan(str, period);
			}
		}
		
		// 정지되어있던 사람들은 정지기간 추가
		if(being.size() > 0) {
			result += adminMapper.addBanPeriod(being, period);
		}
		System.out.println("zz"+result);
		System.out.println("zz"+cnt);
		return result;
	}

	// 회원삭제
	@Override
	public int deleteMember(List<String> list) {
		return adminMapper.deleteMember(list);
	}
	// 회원 정지해제
	@Override
	public int returnNorm(List<String> list) {
		return adminMapper.returnNorm(list);
	}

}
