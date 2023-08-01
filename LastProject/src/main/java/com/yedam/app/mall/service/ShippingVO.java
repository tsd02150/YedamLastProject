package com.yedam.app.mall.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ShippingVO {
	
	private String shipNo;
	private String orderNo;
	private String membNo;
	private String shipSt;
	private String orderSt;
	private int billNo;
	private int orderAm;
	private String rec;
	private String recTel;
	private String addr;
	private String detaAddr;
	
	private Date orderDt;
    private String startDate;
    private String endDate;
	private int cnt;
	private String orderDetaNo;
	private String rodtNo;
	private String nm;
	private int page;
	private String thumb;
	private String prdtNo;
	private int payAn;
	private String name;
	private String tel;
	
	
}
