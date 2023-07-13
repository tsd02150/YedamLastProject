package com.yedam.app.community.service;

import java.util.List;

public interface BoardService {
	// 해당 게시판 이름
	public String getBoardName(String commonCd);
	// 게시판 목록 정보
	public List<BoardVO> getBoardList(BoardVO vo);
	// 자유게시판 6개 출력
	public List<BoardVO> getFreeBoardTop6();
	// 주식게시판 6개 출력
	public List<BoardVO> getStockBoardTop6();
	// 게시판 개수
	public int getBoardCount(BoardVO vo);
	// 게시물 작성 카테고리 정보
	public List<BoardVO> getCtgr(String commonCd);
	// 멤버번호
	public String getMembNo(String nick);
	// 게시물 추가
	public boolean insertBoard(BoardVO vo); 
}
