package com.yedam.app.member.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class BuyOrderVO {
//	BUY_NO	VARCHAR2(10 BYTE)
//	ITEM_NO	VARCHAR2(10 BYTE)
//	MEMB_NO	VARCHAR2(10 BYTE)
//	PRC	NUMBER
//  RMN_CNT	NUMBER
//	BUY_DT	DATE
	
	private String buyNo;
	private String itemNo;
	private String membNo;
	private String commonCd;
	private String nm;
	private int prc;
	private int rmnCnt;
	private int point;
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date buyDt;
}
