package com.yedam.app.member.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class StopVO {
//	STOP_NO  NOT NULL VARCHAR2(10) 
//	MEMB_NO  NOT NULL VARCHAR2(10) 
//	START_DT NOT NULL DATE         
//	END_DT   NOT NULL DATE
	
	private String stopNo;
	private String membNo;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date startDt;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date endDt;
}
