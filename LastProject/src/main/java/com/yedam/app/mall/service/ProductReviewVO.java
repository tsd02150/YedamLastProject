package com.yedam.app.mall.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ProductReviewVO {
	
	private String revNo;
	private String prdtNo;
	private String membNo;
	private String cntn;
	private String img;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date drwupDt;
	private int maks;

}
