package com.yedam.app.community.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.community.mapper.QuestionMapper;
import com.yedam.app.community.service.FaqVO;
import com.yedam.app.community.service.QuestionService;
import com.yedam.app.community.service.QuestionVO;

@Service
public class QuestionServiceImpl implements QuestionService {
	@Autowired
	QuestionMapper questionMapper;

	@Override
	public List<FaqVO> getFaqList(FaqVO vo) {
		return questionMapper.getFaqList(vo);
	}

	@Override
	public int getFaqCount(FaqVO vo) {
		return questionMapper.getFaqCount(vo);
	}

	@Override
	public List<QuestionVO> getQnaList(QuestionVO vo) {
		return questionMapper.getQnaList(vo);
	}

	@Override
	public int getQnaCount(QuestionVO vo) {
		return questionMapper.getQnaCount(vo);
	}
	
	

}
