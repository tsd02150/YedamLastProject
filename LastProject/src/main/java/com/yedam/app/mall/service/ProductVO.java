package com.yedam.app.mall.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ProductVO {
	
	private String prdtNo;
	private String nm;
	private int prc;
	private int dcRate;
	private String desct;
	private String thumb;
	private String img;
	private int stcCnt;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date drwupDt;
	private String commonCd;

}
