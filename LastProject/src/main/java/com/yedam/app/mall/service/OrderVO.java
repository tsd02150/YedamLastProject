package com.yedam.app.mall.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class OrderVO {

	private String orderNo;
	private String membNo;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date orderDt;
	private int dcRate;
	private String shpcst;
	private int orderAm;
	private String orderSt;
	private int payAn;
	
	private int prc;
	private String prdt_no;
	private String id;

	private String orderDetaNo;
	private String prdtNo;
	private int cnt;
	private String nm;
	private int point;
	private String thumb;
	
	// 주문 필요정보
	private String tel;
	private String mobile;
	private String addrNo;
	private String zip;
	private String addr;
	private String detaAddr;

	// 정렬
	private String order;

}
