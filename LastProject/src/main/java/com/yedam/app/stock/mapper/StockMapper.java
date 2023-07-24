package com.yedam.app.stock.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yedam.app.community.service.BoardVO;
import com.yedam.app.stock.service.AlarmVO;
import com.yedam.app.stock.service.InqVO;
import com.yedam.app.stock.service.ItemInfoVO;
import com.yedam.app.stock.service.ItemVO;
import com.yedam.app.stock.service.PossStockVO;
import com.yedam.app.stock.service.StockVO;

public interface StockMapper {
	// 카테고리 선택
	public List<Map<String,Object>> getThemeList(String code);
	// 공통코드 / 카테고리로 종목정보조회
	public ItemVO getItemInfo(String value);
	//아이템 번호로
	public StockVO itemNoGetInfo(String itemNo);
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
	// 거래량 top 5 순위 목록
	public List<StockVO> topVolChart();
	// 오늘날짜 변동률
	public List<StockVO> getPrcPercent(String type);
	// 호가 데이터
	public List<Map<String,Object>> orderTable(@Param("type")String type , @Param("itemNo")String itemNo);
	// 유저 단일 보유 종목 수량과 수익률
	public PossStockVO getPossStock(@Param("itemNo") String itemNo , @Param("membNo")String membNo);
	// 유저 보유 포인트
	public Integer getUserPoint(String membNo);
	// 주문 프로시저
	public void callOrderProd(Map<String,Object> params);
	// 체결 프로시저
	public void callTaProd(Map<String,Object> params);
	// 주문번호로 주문일시 가져오기
	public Date getOrderDt(String orderNo);
	// 오늘 종목 주가정보
	public List<ItemInfoVO> todayItemInfo();
	// 장마감시 종목 당일 정보 넣기
	public int insertItemInfo(ItemInfoVO vo);
	//스케줄러 작업
	public void deleteJob();
	// 조회수 증가
	public int updateInq(String itemNo);
	// 일일 차트
	public List<ItemInfoVO> dayChart(String itemNo);
	// 주간 월간 차트
	public List<ItemInfoVO> weekMonthChart(@Param("itemNo")String itemNo ,@Param("type") String type);
	// 알람데이터 추가
	public int insertStockAlarm(@Param("membNo")String membNo , @Param("cntn")String cntn);
	// 미확인 알람 데이터 가져오기
	public List<AlarmVO> nonCheckedAlarm(String membNo);
	// 미확인 알람 확인 알람으로 바꾸기
	public int stockAlmChk(String almNo);
	// 체결이 없는 종목의 전날 정보
	public List<ItemInfoVO> nonTaInfo(List<Integer> itemNos);
}
