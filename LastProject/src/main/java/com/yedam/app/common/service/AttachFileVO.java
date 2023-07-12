package com.yedam.app.common.service;

import java.sql.Date;

import lombok.Data;

@Data
public class AttachFileVO {
	private String atchNo;
	private String atchNm;
	private String boardNo;
	private String notiNo;
	private Date drwupDt;
	private String prdtNo;
	private String path;
	private long fileSize;
	private String atchOriginNm;
}
