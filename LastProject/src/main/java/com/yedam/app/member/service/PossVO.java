package com.yedam.app.member.service;

import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;

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
	private double rate;
	private double raise;
	private String commonCd;
	private String membNo;
	private int stockCnt;
	private String taDt;
	
	//@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date dt;
}
