package com.yedam.app.admin.service;

import java.util.List;
import java.util.Map;

import com.yedam.app.community.service.BoardVO;
import com.yedam.app.community.service.FaqVO;
import com.yedam.app.community.service.NoticeVO;
import com.yedam.app.community.service.QuestionVO;
import com.yedam.app.mall.service.CommonCdVO;
import com.yedam.app.mall.service.OrderVO;
import com.yedam.app.mall.service.ProductVO;


public interface AdminService {
	public Map<String,Object> getMembList(int page , int perPage);
	public int memberBan(List<String> list , Integer period);
	public int deleteMember(List<String> list);
	public int returnNorm(List<String> list);
	public Map<String,Object> reportList(int page , int perPage);
	public Map<String,Object> reportInfo(String accused , String boardNo);
	public int rprtStChange(String rprtNo);
	public int deleteReport(List<String> list);
	public Map<String, Object> noticeList(int page, int perPage);
	public Map<String , Object > noticeDetail(String notiNo);
	public Map<String, Object> qnaList(int page, int perPage);
	public Map<String, Object> faqList(int page, int perPage);
	public Map<String, Object> boardList(int page, int perPage);
	public Map<String, Object> chatList(int page, int perPage);
	public Map<String , Object> addNotice(NoticeVO vo);
	public Map<String , Object> modifyNotice(NoticeVO vo);
	public int deleteNotice(List<String> list);
	public FaqVO faqDetail(String faqNo);
	public QuestionVO qnaDetail(String qstNo);
	public Map<String , Object> modifyFaq(FaqVO vo);
	public Map<String , Object> modifyQna(QuestionVO vo);
	public int deleteFaq(List<String> list);
	public int deleteQna(List<String> list);
	public int addFaq(FaqVO vo);
	public int deleteBoard(List<String> list);
	public BoardVO boardDetail(String boardNo);
	public List<String> getRoomList();
	public int nonChkRptCnt();
	
	public Map<String, Object> orderList(int page, int perPage);
	public Map<String , Object> modifyOrder(OrderVO ordVO);
	public Map<String, Object> productList(int page, int perPage);
	public int deleteProduct(List<String> list);
	public Map<String, Object> addProduct(ProductVO prdtVO);
	public Map<String , Object> modifyProduct(ProductVO prdtVO);
	public Map<String , Object > productDetail(String prdtNo);
	public void addCommonCd(ProductVO prdtVO);
	
	public int orderStUpdate(OrderVO list);
	public int deleteOrder(List<String> list);
	public int deleteCommonCd(List<String> list);
	
	
}
