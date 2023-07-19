package com.yedam.app.member.service;

import lombok.Data;

@Data
public class PossVO {

	private String possNo;
	private String itemNo;
	private String tnm;
	private int stock_cnt;
	private int tradePrc;
	private int tprc;
	private int change;
	private int rate;
}
