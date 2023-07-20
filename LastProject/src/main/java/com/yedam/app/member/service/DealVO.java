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
	
//	TA_NO, STOCK_CNT, TA_PRC, TA_DT, BUY_MEMB_NO AS MEMB_NO, 'B' AS kind, ITEM_NO
	private String taNo;
	private int stockCnt;
	private int taPrc;
	private String taDt;
	private String kind;
	
}
