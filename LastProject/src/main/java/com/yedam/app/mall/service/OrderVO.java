package com.yedam.app.mall.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;


@Data
public class OrderVO {
	
	private String orderNo;
	private String membNo;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date orderDt;
	private int dcRate;
	private String shpcst;
	private String orderAm;
	private String orderSt;
	private int payAn;
	
}
