package com.yedam.app.common.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.common.service.MainService;
import com.yedam.app.community.service.BoardService;
import com.yedam.app.community.service.NoticeService;
import com.yedam.app.community.service.QuestionService;

@Controller
public class MainController {
	
	@Autowired
	MainService mainService;
	@Autowired
	BoardService boardService;
	@Autowired
	NoticeService noticeService;
	@Autowired
	QuestionService questionService;
	
	// 프로젝트 메인페이지
	@GetMapping("/")
	public String mainPage(Model model, HttpSession session) {
		model.addAttribute("freeBoardList",mainService.getFreeBoardTop6());
		model.addAttribute("stockBoardList",mainService.getStockBoardTop6());
		model.addAttribute("noticeList",mainService.getNoticeTop6());
		model.addAttribute("qnaList",mainService.getQnaTop6());
		model.addAttribute("farmRank",mainService.getFarmRank());
		model.addAttribute("seaRank",mainService.getSeaRank());
		model.addAttribute("increaseStock",mainService.getIncreaseStock());
		model.addAttribute("decreaseStock",mainService.getDecreaseStock());

		return "main/main";
	}
	
	// 농수산물 관련 뉴스 정보
	@GetMapping("getNewsLists")
	@ResponseBody
	public String getNewsLists() {
		return mainService.getNews();
	}
}
