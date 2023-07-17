package com.yedam.app.community.service;

import java.util.List;

public interface QuestionService {
	// faq리스트
	public List<FaqVO> getFaqList(FaqVO vo);
	// faq개수
	public int getFaqCount(FaqVO vo);
	// qna리스트
	public List<QuestionVO> getQnaList(QuestionVO vo);
	// qna top6
	public List<QuestionVO> getQnaTop6();
	// qna개수
	public int getQnaCount(QuestionVO vo);
	// qna 작성
	public boolean insertQna(QuestionVO vo);
	// qna 상세
	public QuestionVO getQnaDetail(String qstNo);
	// 조회수 증가
	public void increaseInq(String qstNo);
}
