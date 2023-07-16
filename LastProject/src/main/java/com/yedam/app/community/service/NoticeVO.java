package com.yedam.app.community.service;

import java.sql.Date;

import lombok.Data;

@Data
public class NoticeVO {
	private String notiNo;
	private String membNo;
	private String ttl;
	private String cntn;
	private Date drwupDt;
	private int inq;
	
	private int page;
	private int totalCnt;
}
