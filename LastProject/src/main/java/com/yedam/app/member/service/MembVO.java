package com.yedam.app.member.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class MembVO {

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

	private String interestNo;
	private String itemNo;
	
	private String addrNo;
	private String baseAddrYn;
	private String zip;
	private String addr;
	private String detaAddr;
	

}
