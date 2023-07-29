package com.yedam.app.mall.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ShippingVO {
	
	private String shipNo;
	private String orderNo;
	private String membNo;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date shipSt;
	private int billNo;
	private String rec;
	private String recTel;
	private String addr;
	private String detaAddr;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date orderDt;
	private int cnt;
	private String orderDetaNo;
	private String rodtNo;
	private String nm;
	private int page;
	private String thumb;
	
}
