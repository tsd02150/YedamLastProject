package com.yedam.app.community.mapper;

import java.util.List;

import com.yedam.app.community.service.BoardVO;

public interface BoardMapper {
	// 해당 게시판 이름
	public String getBoardName(String commonCd);
	// 게시판 리스트
	public List<BoardVO> getBoardList(BoardVO vo);
	// 자유게시판 6개 출력
	public List<BoardVO> getFreeBoardTop6();
	// 주식게시판 6개 출력
	public List<BoardVO> getStockBoardTop6();
	// 게시물 개수
	public int getBoardCount(BoardVO vo);
	// 게시물 작성 카테고리 정보
	public List<BoardVO> getCtgr(String commonCd);
}
