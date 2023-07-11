package com.yedam.app.community.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.community.service.BoardService;
import com.yedam.app.community.service.BoardVO;

@Controller
@RequestMapping("community")
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	// 게시판 목록 출력
	@GetMapping("boardList")
	public String getBoard(Model model,BoardVO vo) {
		model.addAttribute("boardCode",vo.getCommonCd().substring(0,2));
		model.addAttribute("boardName",boardService.getBoardName(vo.getCommonCd().substring(0,2)));
		return "community/boardList";
	}
	
	// 게시판 전체
	@PostMapping("getBoardList")
	@ResponseBody
	public List<BoardVO> getBoardList(BoardVO vo) {  
		return boardService.getBoardList(vo);
	}
	
	// 게시물 개수
	@PostMapping("getBoardCount")
	@ResponseBody
	public int getBoardCount(BoardVO vo) {
		return boardService.getBoardCount(vo);
	}
	
}
