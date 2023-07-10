package com.yedam.app.member.service;

import lombok.Data;

@Data
public class InterestVO {
//	COMMON_CD NOT NULL VARCHAR2(6)  
//	item_no, nm, common_cd
//	SELECT item_no, nm, common_cd, cl_prc FROM item JOIN item_info USING(item_no)
	
	private String commonCd;
	private String itemNo;
	private String nm;
	private int clPrc;
}
