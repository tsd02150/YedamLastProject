package com.yedam.app.stock.service;

import java.util.Date;

import lombok.Data;

@Data
public class ItemInfoVO {

	private String itemNo;
	private Integer vol;
	private Integer opPrc;
	private Integer clPrc;
	private Integer lPrc;
	private Integer hPrc;
	private Integer inq;
	private Date taDt;
	
	public ItemInfoVO() {}
	
	public ItemInfoVO(String itemNo) {
		this.itemNo = itemNo;
		this.vol=0;
		this.opPrc=0;
		this.clPrc=0;
		this.lPrc=0;
		this.hPrc=0;
		this.inq=0;
		this.taDt=new Date();
	}
}
