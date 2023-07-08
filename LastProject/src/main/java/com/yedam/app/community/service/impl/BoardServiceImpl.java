package com.yedam.app.community.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.community.mapper.BoardMapper;
import com.yedam.app.community.service.BoardService;
import com.yedam.app.community.service.BoardVO;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	BoardMapper boardMapper;

	@Override
	public List<BoardVO> getBoardList(BoardVO vo) {
		return boardMapper.getBoardList(vo);
	}
	
}
