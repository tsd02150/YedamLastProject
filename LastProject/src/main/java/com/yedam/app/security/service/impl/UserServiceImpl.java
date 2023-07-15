package com.yedam.app.security.service.impl;

import java.net.http.HttpRequest;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.yedam.app.security.mapper.UserMapper;
import com.yedam.app.security.service.UserService;
import com.yedam.app.security.service.UserVO;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
	@Autowired
	UserMapper userMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("----------------------------------------------");
		System.out.println(username);
		UserVO userVO = userMapper.loginCheck(username);
		
		System.out.println(userVO);
		System.out.println("----------------------------------------------");
		
		if(userVO == null) {
			System.out.println("userVO == null");
			throw new UsernameNotFoundException("no user");
		}
		return userVO;
	}

	@Override
	public UserVO loginCheck(String id) {
		return userMapper.loginCheck(id);
	}
}
