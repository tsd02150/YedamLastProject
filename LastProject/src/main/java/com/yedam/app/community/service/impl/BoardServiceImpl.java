package com.yedam.app.community.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.community.mapper.BoardMapper;
import com.yedam.app.community.service.BoardService;
import com.yedam.app.community.service.BoardVO;
import com.yedam.app.community.service.CommentsVO;
import com.yedam.app.member.service.MembVO;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	BoardMapper boardMapper;

	@Override
	public String getBoardName(String commonCd) {
		return boardMapper.getBoardName(commonCd);
	}

	@Override
	public List<BoardVO> getBoardList(BoardVO vo) {
		return boardMapper.getBoardList(vo);
	}

	@Override
	public List<BoardVO> getFreeBoardTop6() {
		return boardMapper.getFreeBoardTop6();
	}

	@Override
	public List<BoardVO> getStockBoardTop6() {
		return boardMapper.getStockBoardTop6();
	}

	@Override
	public int getBoardCount(BoardVO vo) {
		return boardMapper.getBoardCount(vo);
	}

	@Override
	public List<BoardVO> getCtgr(String commonCd) {
		return boardMapper.getCtgr(commonCd);
	}

	@Override
	public String getMembNo(String nick) {
		return boardMapper.getMembNo(nick);
	}

	@Override
	public boolean insertBoard(BoardVO vo) {
		return boardMapper.insertBoard(vo)>0;
	}

	@Override
	public BoardVO getBoardDetail(String boardNo) {
		return boardMapper.getBoardDetail(boardNo);
	}

	@Override
	public void increaseInquery(String boardNo) {
		boardMapper.increaseInquery(boardNo);
	}

	@Override
	public MembVO getMembInfo(String membNo) {
		return boardMapper.getMembInfo(membNo);
	}

	@Override
	public List<CommentsVO> getComments(String boardNo) {
		return boardMapper.getComments(boardNo);
	}

	@Override
	public boolean addRcom(BoardVO vo) {
		return boardMapper.addRcom(vo)>0;
	}

	@Override
	public boolean addNrcom(BoardVO vo) {
		return boardMapper.addNrcom(vo)>0;
	}

	@Override
	public boolean deleteBoard(BoardVO vo) {
		return boardMapper.deleteBoard(vo)>0;
	}

	@Override
	public boolean modifyBoard(BoardVO vo) {
		return boardMapper.modifyBoard(vo)>0;
	}

	@Override
	public boolean addCommentRcom(CommentsVO vo) {
		return boardMapper.addCommentRcom(vo)>0;
	}

	@Override
	public boolean addCommentNrcom(CommentsVO vo) {
		return boardMapper.addCommentNrcom(vo)>0;
	}

	@Override
	public boolean insertComment(CommentsVO vo) {
		return boardMapper.insertComment(vo)>0;
	}

	@Override
	public CommentsVO getComment(String commNo) {
		return boardMapper.getComment(commNo);
	}

	@Override
	public boolean deleteComment(CommentsVO vo) {
		return boardMapper.deleteComment(vo)>0;
	}

	@Override
	public boolean insertSubComment(CommentsVO vo) {
		return boardMapper.insertSubComment(vo)>0;
	}

	
}
