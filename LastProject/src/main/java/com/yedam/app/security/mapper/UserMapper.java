package com.yedam.app.security.mapper;

import com.yedam.app.security.service.UserVO;

public interface UserMapper {
	public UserVO loginCheck(String id);
}
