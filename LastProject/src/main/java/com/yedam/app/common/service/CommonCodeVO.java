package com.yedam.app.common.service;

import java.util.List;

import lombok.Data;

@Data
public class CommonCodeVO {
	private String commonCd;
	private String ctgr;
	private String hCd;
	private String hCtgr;
	List<CommonCodeVO> childList;
}
