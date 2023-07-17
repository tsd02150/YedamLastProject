package com.yedam.app.stock.service;

import lombok.Data;

@Data
public class StockOrderVO {
	private String order_type;
	private String order_item_no;
	private String order_memb_no;
	private int order_prc;
	private int order_rmn_cnt;
}
