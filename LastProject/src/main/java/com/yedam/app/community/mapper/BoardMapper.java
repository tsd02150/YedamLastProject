package com.yedam.app.community.mapper;

import java.util.List;

import com.yedam.app.community.service.BoardVO;

public interface BoardMapper {
	// 해당 게시판 이름
	public String getBoardName(String commonCd);
	// 게시판 리스트
	public List<BoardVO> getBoardList(BoardVO vo);

}
