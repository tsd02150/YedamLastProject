package com.yedam.app.community.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.community.service.BoardVO;
import com.yedam.app.community.service.NoticeService;
import com.yedam.app.community.service.NoticeVO;

@Controller
@RequestMapping("community")
public class NoticeController {
	@Autowired
	NoticeService noticeService;
	
	@GetMapping("noticeList")
	public String noticeList(Model model) {
		model.addAttribute("startPage",1);
		return "community/noticeList";
	}
	
	@PostMapping("getNoticeList")
	@ResponseBody
	public List<NoticeVO> getNoticeList(NoticeVO vo){
		return noticeService.getNoticeList(vo);
	}
	
	@PostMapping("getNoticeCount")
	@ResponseBody
	public int getNoticeCount() {
		return noticeService.getNoticeCount();
	}
	
	@GetMapping("noticeDetail")
	public String noticeDetail(Model model,NoticeVO vo) {
		noticeService.increaseInquery(vo.getNotiNo());
		model.addAttribute("notice",noticeService.getNoticeDetail(vo.getNotiNo()));
		return "community/noticeDetail";
	}
}
