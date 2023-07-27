package com.yedam.app.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import lombok.Getter;


@Getter
public class PrincipalDetails implements UserDetails, OAuth2User {
	private UserVO userVO;
	private Map<String, Object> attributes;
	
	@Autowired
	UserService userService;

	// 소셜 로그인
	public PrincipalDetails(UserVO userVO, Map<String, Object> attributes) {
		this.userVO = userVO;
		this.attributes = attributes;
	}

	public UserVO getUserVO() {
		return this.userVO;
	}

	// 일반 로그인
	public PrincipalDetails(UserVO userVO) {
		this.userVO = userVO;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public String getName() {
		String sub = attributes.get("sub").toString();
		return sub;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>();
		Date currentDate = new Date();
		
		if (userVO.getNm().equals("admin")) {
			list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			list.add(new SimpleGrantedAuthority("ROLE_USER"));
		} else {
	        Date endDt = userVO.getEndDt(); 
	        if (endDt != null && endDt.after(currentDate)) {
	            list.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
	        } else {
	            list.add(new SimpleGrantedAuthority("ROLE_USER"));
	        }
	    }

		System.out.println("권한" + list);
		return list;
	}

	@Override
	public String getPassword() {
		return userVO.getPwd();
	}

	@Override
	public String getUsername() {
		return userVO.getId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
