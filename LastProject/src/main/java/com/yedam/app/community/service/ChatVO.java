package com.yedam.app.community.service;

import java.sql.Date;

import lombok.Data;

@Data
public class ChatVO {
	private String chatNo;
	private String cntn;
	private Date drwupDt;
	private String roomNo;
	private String anonNick;
	private String membNo;
}
