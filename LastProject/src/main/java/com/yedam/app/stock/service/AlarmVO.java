package com.yedam.app.stock.service;

import java.util.Date;

import lombok.Data;

@Data
public class AlarmVO {
//	ALARM_NO
//	MEMB_NO
//	CNTN
//	ALARM_DT
//	CHECKED
	private String alarmNo;
	private String membNo;
	private String cntn;
	private Date alarmDt;
	private String checked;
	private String aType;
}
