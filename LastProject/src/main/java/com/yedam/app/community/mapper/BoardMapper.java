package com.yedam.app.community.mapper;

import java.util.List;

import com.yedam.app.community.service.BoardVO;
import com.yedam.app.community.service.CommentsVO;
import com.yedam.app.member.service.MembVO;

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
	// 멤버번호
	public String getMembNo(String nick);
	// 게시물 추가
	public int insertBoard(BoardVO vo);
	// 게시물 정보
	public BoardVO getBoardDetail(String boardNo);
	// 게시물 조회수 증가
	public void increaseInquery(String boardNo);
	// 게시물 작성자 정보
	public MembVO getMembInfo(String membNo);
	// 게시물 댓글 정보
	public List<CommentsVO> getComments(String boardNo);
}
