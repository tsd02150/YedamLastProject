package com.yedam.app.common.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.common.service.MainService;
import com.yedam.app.member.service.MembVO;

@Controller
public class MainController {
	
	@Autowired
	MainService mainService;
	
	// 프로젝트 메인페이지
	@GetMapping("/")
	public String mainPage(Model model, HttpSession session) {	
		return "main/main";
	}
	
	// 농수산물 관련 뉴스 정보
	@GetMapping("getNewsLists")
	@ResponseBody
	public String getNewsLists() {
		return mainService.getNews();
	}
}
