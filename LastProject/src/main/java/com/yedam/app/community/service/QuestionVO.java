package com.yedam.app.community.service;

import java.sql.Date;

import lombok.Data;

@Data
public class QuestionVO {
	private String qstNo;
	private String membNo;
	private String ttl;
	private String cntn;
	private Date drwupDt;
	private String rply;
	private String rplyDt;
	private int inq;
	
	private int page;
	private String nick;
}
