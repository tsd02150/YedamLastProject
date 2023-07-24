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
	// qna 작성
	public int insertQna(QuestionVO vo);
	// qna 상세
	public QuestionVO getQnaDetail(String qstNo);
	// 조회수 증가
	public void increaseInq(String qstNo);
	// qna 삭제
	public int deleteQna(String qstNo);
}
