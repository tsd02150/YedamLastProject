package com.yedam.app.stock.service;

import lombok.Data;

@Data
public class InqVO {
//nm , a.item_no , inq , cl_prc , tprc , change , rate
	private String nm;
	private String itemNo;
	private Integer change;
	private Integer rate;
}
