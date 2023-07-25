package com.yedam.app.admin.service;

import java.util.Map;


public interface AdminService {
	public Map<String,Object> getMembList(int page , int perPage);
	public int memberBan(String membNo , Integer period);
}
