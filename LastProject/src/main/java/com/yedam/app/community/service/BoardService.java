package com.yedam.app.community.service;

import java.util.List;

public interface BoardService {
	// 해당 게시판 이름
	public String getBoardName(String commonCd);
	// 게시판 목록 정보
	public List<BoardVO> getBoardList(BoardVO vo);

}
