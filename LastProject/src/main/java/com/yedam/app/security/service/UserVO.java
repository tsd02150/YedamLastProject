package com.yedam.app.security.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserVO{
	private String membNo;
	private String nm;
	private String tnm;
	private String id;
	private String pwd;
	private String nick;
	private String email;
	private String tel;
	private String mobile;
	private String path;
	private int point;
	private String tempPwd;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Date joinDt;

	private String interestNo;
	private String itemNo;
	private String commonCd;
	private String addrNo;
	private String baseAddrYn;
	private String zip;
	private String addr;
	private String detaAddr;
	private int tprc;
	private int change;
	private int rate;
	
	private String prdtNo;
	private String thumb;
	private String bskNo;

	private String stopNo;
	private String startDt;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endDt;
	private String stopPeriod;
	
}
