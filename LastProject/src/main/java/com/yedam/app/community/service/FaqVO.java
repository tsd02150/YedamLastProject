package com.yedam.app.community.service;

import lombok.Data;

@Data
public class FaqVO {
	private String faqNo;
	private String ttl;
	private String cntn;
	private String category;
	
	private int page;
}
