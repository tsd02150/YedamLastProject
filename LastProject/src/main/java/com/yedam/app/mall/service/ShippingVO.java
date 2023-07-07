package com.yedam.app.mall.service;

import lombok.Data;



@Data
public class ShippingVO {
	
	private String shipNo;
	private String orderNo;
	private String shipSt;
	private int billNo;
	private String rec;
	private String recTel;
	private String addr;
	private String detaAddr;

}
