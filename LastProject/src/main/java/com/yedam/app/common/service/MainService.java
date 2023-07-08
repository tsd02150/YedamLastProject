package com.yedam.app.common.service;

public interface MainService {
	
	// 차트정보
	public Object getChart();
	
	// 주식정보
	public Object getStock();
	
	// 공지사항 정보
	public Object getNotice();
	
	// QnA 정보
	public Object getQuestion();
	
	// 자유게시판 정보
	public Object getFreeBoard();
	
	// 주식게시판 정보
	public Object getStockBoard();
	
	// 농수산물 관련 뉴스 정보
	public String getNews();
		
}
