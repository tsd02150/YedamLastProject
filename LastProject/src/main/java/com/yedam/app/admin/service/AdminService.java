package com.yedam.app.admin.service;

import java.util.List;
import java.util.Map;

import com.yedam.app.community.service.BoardVO;
import com.yedam.app.community.service.CommentsVO;
import com.yedam.app.community.service.FaqVO;
import com.yedam.app.community.service.NoticeVO;
import com.yedam.app.community.service.ReportVO;


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
	public NoticeVO noticeDetail(String notiNo);
	public Map<String, Object> qnaList(int page, int perPage);
	public Map<String, Object> faqList(int page, int perPage);
	public Map<String, Object> boardList(int page, int perPage);
	public Map<String, Object> chatList(int page, int perPage);
}
