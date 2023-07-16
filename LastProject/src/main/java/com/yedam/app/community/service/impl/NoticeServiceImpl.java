package com.yedam.app.community.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.community.mapper.NoticeMapper;
import com.yedam.app.community.service.NoticeService;
import com.yedam.app.community.service.NoticeVO;

@Service
public class NoticeServiceImpl implements NoticeService {
	
	@Autowired
	NoticeMapper noticeMapper;

	@Override
	public List<NoticeVO> getNoticeList(NoticeVO vo) {
		return noticeMapper.getNoticeList(vo);
	}

	@Override
	public int getNoticeCount() {
		return noticeMapper.getNoticeCount();
	}

	@Override
	public void increaseInquery(String notiNo) {
		noticeMapper.increaseInquery(notiNo);		
	}

	@Override
	public NoticeVO getNoticeDetail(String notiNo) {
		return noticeMapper.getNoticeDetail(notiNo);
	}

	@Override
	public List<NoticeVO> getNoticeTop6() {
		return noticeMapper.getNoticeTop6();
	}
	
}
