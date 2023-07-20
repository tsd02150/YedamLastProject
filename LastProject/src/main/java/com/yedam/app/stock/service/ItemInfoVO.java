package com.yedam.app.stock.service;

import lombok.Data;

@Data
public class ItemInfoVO {
//	INFO_NO 
//	,ITEM_NO
//	, TA_DT
//	, VOL
//	, OP_PRC
//	, CL_PRC
//	, L_PRC
//	, H_PRC
//	, RATE
//	, INQ
	private String itemNo;
	private Integer vol;
	private Integer opPrc;
	private Integer clPrc;
	private Integer lPrc;
	private Integer hPrc;
	private Integer inq;
	
	public ItemInfoVO() {}
	
	public ItemInfoVO(String itemNo) {
		this.itemNo = itemNo;
		this.vol=0;
		this.opPrc=0;
		this.clPrc=0;
		this.lPrc=0;
		this.hPrc=0;
		this.inq=0;
	}
}
