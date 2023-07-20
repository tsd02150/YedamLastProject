package com.yedam.app.member.service;

import lombok.Data;

@Data
public class PossVO {

	private String possNo;
	private String itemNo;
	private String tnm;
	private int stock_cnt;
	private int tradePrc;
	private int nowPrc;
	private int tprc;
	private int change;
	private int rate;
	private String commonCd;
	private String membNo;
	private int stockCnt;
}
