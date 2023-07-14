package com.yedam.app.community.service;

import java.sql.Date;

import lombok.Data;

@Data
public class CommentsVO {
	private String commNo;
	private String boardNo;
	private String cntn;
	private Date drwupDt;
	private Date modDt;
	private int rcom;
	private int nrcom;
	private String hCommNo;
	private String membNo;
	
	private String nick;
}
