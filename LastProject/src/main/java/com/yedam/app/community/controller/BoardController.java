package com.yedam.app.community.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.yedam.app.common.service.CommonCodeVO;
import com.yedam.app.community.service.BoardService;
import com.yedam.app.community.service.BoardVO;
import com.yedam.app.member.service.MembVO;

@Controller
@RequestMapping("community")
public class BoardController {

	@Autowired
	BoardService boardService;

	// 게시판 목록 출력
	@GetMapping("boardList")
	public String getBoard(Model model, BoardVO vo) {
		model.addAttribute("boardCode", vo.getCommonCd().substring(0, 2));
		model.addAttribute("boardName", boardService.getBoardName(vo.getCommonCd().substring(0, 2)));
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

	@GetMapping("addBoard")
	public String addBoardForm(Model model, BoardVO boardVo, HttpSession session) {
		model.addAttribute("boardInfo", boardVo);
		System.out.println(boardService.getCtgr(boardVo.getCommonCd()));
		model.addAttribute("category", boardService.getCtgr(boardVo.getCommonCd()));
		return "community/insertBoard";
	}

	@PostMapping("addBoard")
	@ResponseBody
	public String addBoard(BoardVO vo) {
		System.out.println(vo);
		vo.setMembNo(boardService.getMembNo(vo.getNick()));
		boolean result = boardService.insertBoard(vo);

		if (result) {
			return "success";
		} else {
			return "fail";
		}
	}

}
