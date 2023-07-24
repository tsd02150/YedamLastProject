package com.yedam.app.mall.service;


import lombok.Data;

@Data
public class BasketVO {
	
	private String membNo;
	private String prdtNo;
	private int cnt;
	private String bskNo;
	
	private int dcRate;
	
	////////////////////////
	//상품명
	private String nm;
	//상품가격
	private int prc;
	//싱품 썸네일
	private String thumb;
	
	
	//장바구니 필요정보
	private int point;
	private String tel;
	private String mobile;
	private String addrNo;
	private String zip;
	private String addr;
	private String detaAddr;
	
	@Override
	   public String toString() {
	      return "BasketVO [bskNo=" + bskNo + ", membNo=" + membNo + ", prdtNo=" + prdtNo + ", cnt="
	            + cnt + ", nm=" + nm + ", prc=" + prc + "]";
	   }
	

}
