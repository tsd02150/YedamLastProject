package com.yedam.app.security.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class PrincipalDetails implements UserDetails, OAuth2User {
	private UserVO userVO;
	private Map<String, Object> attributes;
	
	//소셜 로그인
    public PrincipalDetails(UserVO userVO, Map<String, Object> attributes) {
        this.userVO = userVO;
        this.attributes = attributes;
    }
    
    
    public UserVO getUserVO() {
    	return this.userVO;
    }
    
    //일반 로그인
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
		//SimpleDateFormat ddmmyy=new SimpleDateFormat("yyyy-MM-dd");
		Collection<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>();
		Date currentDate = new Date();
		if(userVO.getNm().equals("admin")) {
			list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			list.add(new SimpleGrantedAuthority("ROLE_USER"));
		}
		/*
		 * else if(userVO.getEndDt().after(currentDate)){ list.add(new
		 * SimpleGrantedAuthority("ROLE_MEMBER"));
		 * System.out.println("++++++++++++++++++++++++++++");
		 * System.out.println(userVO.getEndDt().after(currentDate));
		 * System.out.println(userVO.getEndDt()); System.out.println(currentDate);
		 * System.out.println("++++++++++++++++++++++++++++"); }
		 */
		else {
			list.add(new SimpleGrantedAuthority("ROLE_USER"));
		}
		System.out.println("권한"+list);
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