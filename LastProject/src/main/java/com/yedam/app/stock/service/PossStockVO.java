package com.yedam.app.stock.service;

import lombok.Data;

@Data
public class PossStockVO {
	private String itemNo;
	private Integer prc;
	private Integer cnt;
	private Integer rate;
}
