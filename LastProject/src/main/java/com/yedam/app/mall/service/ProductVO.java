package com.yedam.app.mall.service;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yedam.app.common.service.CommonCodeVO;

import lombok.Data;

@Data
public class ProductVO {
	
	private String prdtNo;
	private String nm;
	private int prc;
	private int dcRate;
	private String desct;
	private String thumb;
	private String img;
	private int stcCnt;
	// 작성일
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Date drwupDt;
	private String commonCd;	
	private String ctgr;
	
	private int page;
	
	//구입수량
	private int cnt;
	
	private String membNo;

}
