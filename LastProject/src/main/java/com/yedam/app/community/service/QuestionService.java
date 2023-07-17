package com.yedam.app.community.service;

import java.util.List;

public interface QuestionService {
	// faq리스트
	public List<FaqVO> getFaqList(FaqVO vo);
	// faq개수
	public int getFaqCount(FaqVO vo);
	// qna리스트
	public List<QuestionVO> getQnaList(QuestionVO vo);
	// qna개수
	public int getQnaCount(QuestionVO vo);
}
