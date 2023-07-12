package com.yedam.app.stock.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yedam.app.community.service.BoardVO;
import com.yedam.app.stock.service.InqVO;
import com.yedam.app.stock.service.ItemVO;
import com.yedam.app.stock.service.StockVO;

public interface StockMapper {
	// 카테고리 선택
	public List<Map<String,Object>> getThemeList(String code);
	// 종목정보조회
	public ItemVO getItemInfo(String value);
	//자동완성
	public List<Map<String, Object>> autocomplete(Map<String, Object> paramMap);
	//자동완성사용 정보 출력
	public Map<String,Object> autoInfo(String value);
	// 모든 종목 리스트
	public List<StockVO> allItemList();
	// 모든 종목 수
	public int allItemCnt();
	//조회수 순위 차트
	public List<InqVO> inqChart();
	//유저의 관심종목 불러오기
	public List<StockVO> getIntStock(String membNo);
	//관심종목추가
	public int insertInterestItem(@Param("membNo")String membNo ,@Param("itemNo") String itemNo);
	//관심종목추가 중복체크기능
	public String intItemCheck(@Param("membNo")String membNo ,@Param("itemNo") String itemNo);
	//관심종목 제거기능
	public int deleteIntItem(@Param("membNo")String membNo , @Param("itemNo")String itemNo);
	// 종목명으로 종목번호 받기
	public String nmGetNo(String nm);
	//증권별 최근 게시판 가져오기
	public List<BoardVO> getScBoardList(String itemNo);
}
