package com.yedam.app.stock.service;

import lombok.Data;

@Data
public class StockVO {
	private String itemNo;
	private String sc;
	private String theme;
	private String nm;
	private String tnm;
	private Integer tprc;
	private Integer change;
	private Integer rate;
	private Integer vol;
}
