package com.yedam.app.member.service;

import lombok.Data;

@Data
public class ItemVO {
//	ITEM_NO   NOT NULL VARCHAR2(10) 
//	NM        NOT NULL VARCHAR2(30) 
//	ORIGIN             VARCHAR2(30) 
//	DESCT              VARCHAR2(30) 
//	COMMON_CD NOT NULL VARCHAR2(6)  
//	INQ                NUMBER     
	
	private String itemNo;
	private String nm;
	private String origin;
	private String desct;
	private String commonCd;
	private String inq;
}
