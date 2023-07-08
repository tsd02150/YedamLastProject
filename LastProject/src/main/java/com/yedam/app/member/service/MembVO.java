package com.yedam.app.member.service;

import java.sql.Date;

import lombok.Data;

@Data
public class MembVO {
	
//	memb_no              VARCHAR2(10) NOT NULL ,
//	nm                   VARCHAR2(30) NOT NULL ,
//	id                   VARCHAR2(25) NOT NULL ,
//	pwd                  VARCHAR2(100) NOT NULL ,
//	nick                 VARCHAR2(30) NOT NULL ,
//	email                VARCHAR2(25) NOT NULL ,
//	tel                  VARCHAR2(13) NOT NULL ,
//	point                NUMBER NULL ,
//	temp_pwd             VARCHAR2(100) NULL ,
//	join_dt              DATE NOT NULL
	
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


}
