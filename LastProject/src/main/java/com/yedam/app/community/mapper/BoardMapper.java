package com.yedam.app.community.mapper;

import java.util.List;

import com.yedam.app.community.service.AttachVO;
import com.yedam.app.community.service.BoardVO;
import com.yedam.app.community.service.CommentsVO;
import com.yedam.app.community.service.RcomConfirmVO;
import com.yedam.app.community.service.ReportVO;
import com.yedam.app.member.service.InterestVO;
import com.yedam.app.member.service.MembVO;

public interface BoardMapper {
	// 해당 게시판 이름
	public String getBoardName(String commonCd);
	// 게시판 리스트
	public List<BoardVO> getBoardList(BoardVO vo);
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
	// 추천수 증가
	public int addRcom(BoardVO vo);
	// 비추천수 증가
	public int addNrcom(BoardVO vo);
	// 게시물 삭제
	public int deleteBoard(BoardVO vo);
	// 게시물 수정
	public int modifyBoard(BoardVO vo);
	// 댓글 추천수 증가
	public int addCommentRcom(CommentsVO vo);
	// 댓글 비추천수 증가
	public int addCommentNrcom(CommentsVO vo);
	// 댓글 등록
	public int insertComment(CommentsVO vo);
	// 댓글 등록 알람 추가
	public int insertCommentAlarm(CommentsVO vo);
	// 댓글 단건조회
	public CommentsVO getComment(String commNo);
	// 댓글 삭제
	public int deleteComment(CommentsVO vo);
	// 대댓글 생성
	public int insertSubComment(CommentsVO vo);
	// 첨부파일 가져오기
	public List<AttachVO> getAttachList(String boardNo);
	// 해당 멤버 정보, 관심종목, 종목 가격
	public List<InterestVO> getInerestStockInfo(String membNo);
	// 신고 기능
	public int report(ReportVO vo);
	// 추천 비추천 여부 확인
	public RcomConfirmVO rcomConfirm(RcomConfirmVO vo);
	// 추천 비추천 여부 테이블 추가
	public int addRcomConfirm(RcomConfirmVO vo);
	// 댓글 추천 비추천 여부 확인
	public RcomConfirmVO commentRcomConfirm(RcomConfirmVO vo);
	// 댓글 추천 비추천 여부 테이블 추가
	public int addCommentRcomConfirm(RcomConfirmVO vo);
}
