package com.yedam.app.community.service;

import java.util.List;

public interface NoticeService {
	// 공지사항 목록
	public List<NoticeVO> getNoticeList(NoticeVO vo);
	// 공지사항 개수
	public int getNoticeCount();
	// 조회수 증가
	public void increaseInquery(String notiNo);
	// 공지사항 상세
	public NoticeVO getNoticeDetail(String notiNo);
	
}
