package com.yedam.app.admin.service;

import java.util.List;
import java.util.Map;

import com.yedam.app.community.service.ReportVO;


public interface AdminService {
	public Map<String,Object> getMembList(int page , int perPage);
	public int memberBan(List<String> list , Integer period);
	public int deleteMember(List<String> list);
	public int returnNorm(List<String> list);
	public Map<String,Object> reportList(int page , int perPage);
	
}
