package com.yedam.app.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yedam.app.admin.service.MembManageVO;
import com.yedam.app.community.service.BoardVO;
import com.yedam.app.community.service.CommentsVO;
import com.yedam.app.community.service.FaqVO;
import com.yedam.app.community.service.NoticeVO;
import com.yedam.app.community.service.QuestionVO;
import com.yedam.app.community.service.ReportVO;

public interface AdminMapper {
	// 멤버리스트
	public List<MembManageVO> getMembList(@Param("page")int page , @Param("perPage")int perPage);
	// 멤버 전체 수
	public int membCnt();
	// 회원 정지
	public int memberBan(@Param("membNo") String membNo , @Param("period") Integer period);
	// 회원 삭제
	public int deleteMember(List<String> list);
	// 회원 정지해제
	public int returnNorm(List<String> list);
	// 정지된 회원 조회
	public List<String> bannedMemb();
	// 정지기간 추가
	public int addBanPeriod(@Param("list")List<String> list , @Param("period") Integer period);
	// 신고 리스트
	public List<ReportVO> reportList(@Param("page")int page , @Param("perPage")int perPage);
	// 신고글 전체수
	public int reportCnt();
	// 피고 정보 (글)
	public BoardVO getReportBoard(String boardNo);
	// 피고 정보 (댓글)
	public List<CommentsVO> getReportComments(@Param("accused")String accused ,@Param("boardNo") String boardNo);
	// 신고번호로 글번호조회
	public String rprtNoGetBNo(String rprtNo);
	// 신고글처리 상태 변경
	public int rprtStChange(String rprtNo);
	// 신고글 삭제
	public int deleteReport(List<String> list);
	// 공지사항 리스트
	public List<NoticeVO> noticeList(@Param("page")int page , @Param("perPage")int perPage);
	// 공지사항 수
	public int noticeTotal();
	// 공지사항 한건 조회
	public NoticeVO noticeDetail(String notiNo);
	// qna 리스트
	public List<QuestionVO> qnaList(@Param("page")int page , @Param("perPage")int perPage);
	// qna  전체수
	public int qnaTotal();
	// faq 리스트
	public List<FaqVO> faqList(@Param("page")int page , @Param("perPage")int perPage);
	// faq 전체수
	public int faqTotal();
	// board 리스트
	public List<BoardVO> boardList(@Param("page")int page , @Param("perPage")int perPage);
	// board 전체수
	public int boardTotal();
	// chat 리스트
	public List<BoardVO> chatList(@Param("page")int page , @Param("perPage")int perPage);
	// chat 전체수
	public int chatTotal();
}
