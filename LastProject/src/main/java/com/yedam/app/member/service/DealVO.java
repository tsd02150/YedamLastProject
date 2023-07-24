package com.yedam.app.member.service;

import java.util.Date;


import lombok.Data;

@Data
public class DealVO {
	private String tablename;
	private String orderNo;
	private Date orderDt;
	private int point;
	private String membNo;
	private String itemNo;
	private String nm;
	private String tnm;
	private String startDate;
	private String endDate;
	
	private String taNo;
	private int stockCnt;
	private int taPrc;
	private String taDt;
	private String kind;
	private int page;
	private int totalCnt;
}
