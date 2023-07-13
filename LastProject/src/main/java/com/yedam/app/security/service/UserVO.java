package com.yedam.app.security.service;

import java.sql.Date;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class UserVO implements UserDetails{
	private String membNo;
	private String nm;
	private String id;
	private String pwd;
	private String nick;
	private String email;
	private String tel;
	private int point;
	private String tempPwd;
	private Date joinDt;
	
	private String addrNo;
	private String baseAddrYn;
	private String zip;
	private String addr;
	private String detaAddr;

	private String interestNo;
	private String itemNo;
	
	private String sc;
	private String theme;
	private Integer tprc;
	private Integer change;
	private Integer rate;
	private Integer vol;
	
	
	@Override
	public String getPassword() {
		return pwd;
	}

	@Override
	public String getUsername() {
		return id;
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
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

}
