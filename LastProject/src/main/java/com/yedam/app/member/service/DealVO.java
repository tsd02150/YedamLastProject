package com.yedam.app.member.service;

import java.sql.Date;


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
	private String startDate;
	private String endDate;
}
