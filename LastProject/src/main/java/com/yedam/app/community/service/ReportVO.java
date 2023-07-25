package com.yedam.app.community.service;

import java.util.Date;

import lombok.Data;

@Data
public class ReportVO {
	private String rprtNo;
	private String cntn;
	private Date drwupDt;
	private String rprtSt;
	private int stopTerm;
	private String boardNo;
	private String accused; // 신고 받은 사람
	private String plaintiff; // 신고 한 사람
	private String category;
}
