package com.yedam.app.member.service;

import lombok.Data;

@Data
public class AddrVO {

//	ADDR_NO      NOT NULL VARCHAR2(10)  
//	MEMB_NO      NOT NULL VARCHAR2(10)  
//	BASE_ADDR_YN          CHAR(1)       
//	ZIP          NOT NULL NUMBER        
//	ADDR         NOT NULL VARCHAR2(100) 
//	DETA_ADDR    NOT NULL VARCHAR2(50)
	
	private String addrNo;
	private String membNo;
	private String baseAddrYn;
	private int zip;
	private String addr;
	private String dataAddr;

}
