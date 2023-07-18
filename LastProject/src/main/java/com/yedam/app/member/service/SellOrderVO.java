package com.yedam.app.member.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class SellOrderVO {
	//매도 주문
//	SELL_NO	VARCHAR2(10 BYTE)
//	ITEM_NO	VARCHAR2(10 BYTE)
//	MEMB_NO	VARCHAR2(10 BYTE)
//	PRC	NUMBER
//	RMN_CNT	NUMBER
//	SELL_DT	DATE
//	TRADED_PRC	NUMBER(8,0)
	
	private String sellNo;
	private String membNo;
	private String itemNo;
	private String nm;
	private int prc;
	private int rmnCnt;
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date sellDt;
	private int tradedPrx;
	private String commonCd;
}
