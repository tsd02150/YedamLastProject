package com.yedam.app.mall.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class CouponVO {
//	CPN_NO	VARCHAR2(10 BYTE)
//	DC_RATE	NUMBER
//	START_DT	DATE
//	END_DT	DATE
//	USE_YN	CHAR(1 BYTE)
//	MEMB_NO	VARCHAR2(10 BYTE)
	
	private String cpnNo;
	private int dcRate;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date startDt;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date endDt;
	private String useYn;
	private String membNo;
}
