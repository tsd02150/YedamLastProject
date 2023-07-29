package com.yedam.app.stock.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
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
