package com.yedam.app.mall.service;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class OrderVO {

	private String orderNo;
	private String membNo;
	// 작성일
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
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
	private String email;
	// 주문 필요정보
	private String tel;
	private String mobile;
	private String addrNo;
	private String zip;
	private String addr;
	private String detaAddr;

	// 정렬
	private String order;
	
	private String shipNo;
	private String rec;
	private String recTel;

	private List<String> orderNoList;
}
