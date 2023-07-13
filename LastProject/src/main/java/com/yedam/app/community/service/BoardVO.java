package com.yedam.app.community.service;

import java.sql.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class BoardVO {
	// 게시판번호
	private String boardNo;
	// 회원번호
	private String membNo;
	// 제목
	private String ttl;
	// 내용
	private String cntn;
	// 작성일
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date drwupDt;
	// 조회수
	private int inq;
	// 추천수
	private int rcom;
	// 비추천수
	private int nrcom;
	// 공통코드
	private String commonCd;
	
	///////////////////
	//회원 닉네임
	private String nick;
	// 공통코드 이름
	private String ctgr;
	// 정렬
	private String order;
	// 페이징용
	private int page;
	private int totalCnt;
	
	// 파일첨부
	private List<String> attachFile;
}
