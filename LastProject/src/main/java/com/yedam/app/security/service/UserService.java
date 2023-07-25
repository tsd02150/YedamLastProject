package com.yedam.app.security.service;

import java.util.List;

public interface UserService {
	public UserVO loginCheck(String id); 
	public List<UserVO> stopList(String id);
}
