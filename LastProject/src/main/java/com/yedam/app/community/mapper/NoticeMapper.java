package com.yedam.app.community.mapper;

import java.util.List;

import com.yedam.app.community.service.NoticeVO;

public interface NoticeMapper {
	// 공지사항 목록
	public List<NoticeVO> getNoticeList(NoticeVO vo);
	// 공지사항 개수
	public int getNoticeCount();
	// 조회수 증가
	public void increaseInquery(String notiNo);
	// 공지사항 상세
	public NoticeVO getNoticeDetail(String notiNo);
	// 상단 6개
	public List<NoticeVO> getNoticeTop6();
}
