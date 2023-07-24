package com.yedam.app.common.mapper;

import java.util.List;

import com.yedam.app.community.service.BoardVO;
import com.yedam.app.community.service.NoticeVO;
import com.yedam.app.community.service.QuestionVO;
import com.yedam.app.stock.service.StockVO;

public interface MainMapper {
	// 자유게시판 6개 출력
	public List<BoardVO> getFreeBoardTop6();
	// 주식게시판 6개 출력
	public List<BoardVO> getStockBoardTop6();
	// 공지사항 6개
	public List<NoticeVO> getNoticeTop6();
	// qna top6
	public List<QuestionVO> getQnaTop6();
	// 농산물 랭킹
	public List<StockVO> getFarmRank();
	// 수산물 랭킹
	public List<StockVO> getSeaRank();
	// 상승 종목
	public List<StockVO> getIncreaseStock();
	// 하락 종목
	public List<StockVO> getDecreaseStock();
}
