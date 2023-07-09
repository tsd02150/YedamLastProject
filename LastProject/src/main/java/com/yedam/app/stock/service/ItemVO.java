package com.yedam.app.stock.service;

import lombok.Data;

@Data
public class ItemVO {
//	ITEM_NO   NOT NULL VARCHAR2(10) 
//	NM        NOT NULL VARCHAR2(30) 
//	ORIGIN             VARCHAR2(30) 
//	DESCT              VARCHAR2(30) 
//	COMMON_CD NOT NULL VARCHAR2(6)  
//	INQ                NUMBER     
	
	private String itemNo;
	private String sc;
	private String theme;
	private String nm;
	private Integer tprc;
	private Integer change;
	private Integer rate;
	private String origin;
	private String desct;
}
