package com.yedam.app.community.mapper;

import java.util.List;

import com.yedam.app.community.service.FaqVO;
import com.yedam.app.community.service.QuestionVO;

public interface QuestionMapper {
	// faq리스트
	public List<FaqVO> getFaqList(FaqVO vo);
	// faq개수
	public int getFaqCount(FaqVO vo);
	// qna리스트
	public List<QuestionVO> getQnaList(QuestionVO vo);
	// qna개수
	public int getQnaCount(QuestionVO vo);
}
