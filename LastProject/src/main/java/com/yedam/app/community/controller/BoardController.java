package com.yedam.app.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yedam.app.community.service.BoardService;
import com.yedam.app.community.service.BoardVO;

@Controller
@RequestMapping("community")
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	@GetMapping("boardList")
	public String getBoard(Model model,BoardVO vo) {
		model.addAttribute("boardList",boardService.getBoardList(vo));
		
		return "community/boardList";
	}
}
