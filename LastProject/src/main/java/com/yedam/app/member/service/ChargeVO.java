package com.yedam.app.member.service;

import java.util.Date;

import lombok.Data;

@Data
public class ChargeVO {
//	CHAG_NO	VARCHAR2(10 BYTE)
//	CHAG_PRC	NUMBER
//	CHAG_DT	DATE
//	PAY_AN	NUMBER
//	MEMB_NO	VARCHAR2(10 BYTE)
	
	private String chagNo;
	private int chagPrc;
	private Date chagDt;
	private String membNo;
}
