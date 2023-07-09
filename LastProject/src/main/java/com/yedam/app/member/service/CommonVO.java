package com.yedam.app.member.service;

import lombok.Data;

@Data
public class CommonVO {
//	COMMON_CD NOT NULL VARCHAR2(6)  
//	CTGR      NOT NULL VARCHAR2(30) 
//	H_CD               VARCHAR2(6)  
//	H_CTGR             VARCHAR2(30) 
	
	private String commonCd;
	private String ctgr;
	private String hCd;
	private String hCtgr;
}
