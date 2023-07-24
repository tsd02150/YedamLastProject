package com.yedam.app.community.service;

import java.sql.Date;

import lombok.Data;

@Data
public class AttachVO {
	private String atchNo;
	private String atchNm;
	private String boardNo;
	private String notiNo;
	private Date drwupDt;
	private String prdtNo;
	private String atchOriginNm;
}
