package com.yedam.app.community.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.yedam.app.community.mapper.BoardMapper;
import com.yedam.app.community.service.AttachVO;
import com.yedam.app.community.service.BoardService;
import com.yedam.app.community.service.BoardVO;
import com.yedam.app.community.service.CommentsVO;
import com.yedam.app.community.service.RcomConfirmVO;
import com.yedam.app.community.service.ReportVO;
import com.yedam.app.member.service.InterestVO;
import com.yedam.app.member.service.MembVO;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	BoardMapper boardMapper;
	
	private SimpMessagingTemplate template;
	
	@Autowired 
	public BoardServiceImpl(SimpMessagingTemplate template) {
		this.template = template;
	}

	// 해당 게시판 이름
	@Override
	public String getBoardName(String commonCd) {
		return boardMapper.getBoardName(commonCd);
	}

	// 게시판 목록 정보
	@Override
	public List<BoardVO> getBoardList(BoardVO vo) {
		return boardMapper.getBoardList(vo);
	}

	// 게시판 개수
	@Override
	public int getBoardCount(BoardVO vo) {
		return boardMapper.getBoardCount(vo);
	}

	// 게시물 작성 카테고리 정보
	@Override
	public List<BoardVO> getCtgr(String commonCd) {
		return boardMapper.getCtgr(commonCd);
	}

	// 멤버번호
	@Override
	public String getMembNo(String nick) {
		return boardMapper.getMembNo(nick);
	}

	// 게시물 추가
	@Override
	public boolean insertBoard(BoardVO vo) {
		return boardMapper.insertBoard(vo)>0;
	}

	// 게시물 상세 정보
	@Override
	public BoardVO getBoardDetail(String boardNo) {
		return boardMapper.getBoardDetail(boardNo);
	}

	// 게시물 조회수 증가
	@Override
	public void increaseInquery(String boardNo) {
		boardMapper.increaseInquery(boardNo);
	}

	// 게시물 작성자 정보
	@Override
	public MembVO getMembInfo(String membNo) {
		return boardMapper.getMembInfo(membNo);
	}

	// 게시물 댓글 정보
	@Override
	public List<CommentsVO> getComments(String boardNo) {
		return boardMapper.getComments(boardNo);
	}

	// 추천수 증가
	@Override
	public boolean addRcom(BoardVO vo) {
		return boardMapper.addRcom(vo)>0;
	}

	// 비추천수 증가
	@Override
	public boolean addNrcom(BoardVO vo) {
		return boardMapper.addNrcom(vo)>0;
	}

	// 게시물 삭제
	@Override
	public boolean deleteBoard(BoardVO vo) {
		return boardMapper.deleteBoard(vo)>0;
	}

	// 게시물 수정
	@Override
	public boolean modifyBoard(BoardVO vo) {
		return boardMapper.modifyBoard(vo)>0;
	}
	
	// 댓글 추천수 증가
	@Override
	public boolean addCommentRcom(CommentsVO vo) {
		return boardMapper.addCommentRcom(vo)>0;
	}
	
	// 댓글 비추천수 증가
	@Override
	public boolean addCommentNrcom(CommentsVO vo) {
		return boardMapper.addCommentNrcom(vo)>0;
	}

	// 댓글 등록
	@Override
	public boolean insertComment(CommentsVO vo) {
		return boardMapper.insertComment(vo)>0;
	}
	
	@Override
	public boolean insertCommentAlarm(CommentsVO vo) {
		return boardMapper.insertCommentAlarm(vo)>0;
	}

	// 댓글 단건조회
	@Override
	public CommentsVO getComment(String commNo) {
		return boardMapper.getComment(commNo);
	}
	
	// 댓글 삭제
	@Override
	public boolean deleteComment(CommentsVO vo) {
		return boardMapper.deleteComment(vo)>0;
	}

	// 대댓글 생성
	@Override
	public boolean insertSubComment(CommentsVO vo) {
		return boardMapper.insertSubComment(vo)>0;
	}

	// 첨부파일 가져오기
	@Override
	public List<AttachVO> getAttachList(String boardNo) {
		return boardMapper.getAttachList(boardNo);
	}

	// 관심종목 정보
	@Override
	public List<InterestVO> getInerestStockInfo(String membNo) {
		return boardMapper.getInerestStockInfo(membNo);
	}

	// 신고
	@Override
	public boolean report(ReportVO vo) {
		// 신고시 알람가게하기
		boolean check = boardMapper.report(vo)>0;
		if(check) {
			sendReport();
		}
		return check;
	}
	
	// 신고시 실시간 알람전송
	public void sendReport() {
		System.out.println("신고알람시행@@@@@@@@@@");
		String destination = "/admin/alarm";
		this.template.convertAndSend(destination, "신고가 발생하였습니다");
	}

	// 추천 비추천 여부 확인
	@Override
	public boolean rcomConfirm(RcomConfirmVO vo) {
		return boardMapper.rcomConfirm(vo)!=null;
	}

	// 추천 비추천 여부 테이블 추가
	@Override
	public boolean addRcomConfirm(RcomConfirmVO vo) {
		return boardMapper.addRcomConfirm(vo)>0;
	}

	@Override
	public boolean commentRcomConfirm(RcomConfirmVO vo) {
		return boardMapper.commentRcomConfirm(vo)!=null;
	}

	@Override
	public boolean addCommentRcomConfirm(RcomConfirmVO vo) {
		return boardMapper.addCommentRcomConfirm(vo)>0;
	}

}
