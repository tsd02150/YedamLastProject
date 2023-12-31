package com.yedam.app.community.service;

import java.sql.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
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
	private String hCtgr;
	private String ctgr;
	// 정렬
	private String order;
	// 페이징용
	private int page;
	private int totalCnt;
}
