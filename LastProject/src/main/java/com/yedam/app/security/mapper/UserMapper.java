package com.yedam.app.security.mapper;

import java.util.List;

import com.yedam.app.security.service.UserVO;

public interface UserMapper {
	public UserVO loginCheck(String id);
	public List<UserVO> stopList(String id);
}
