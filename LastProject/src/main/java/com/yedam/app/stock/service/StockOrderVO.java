package com.yedam.app.stock.service;

import lombok.Data;

@Data
public class StockOrderVO {
	private String order_type;
	private String item_no;
	private String memb_no;
	private Integer prc;
	private Integer rmn_cnt;
}
