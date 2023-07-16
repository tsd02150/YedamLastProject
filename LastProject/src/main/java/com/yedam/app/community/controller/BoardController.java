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

import com.yedam.app.common.service.AttachFileVO;
import com.yedam.app.common.service.CommonCodeVO;
import com.yedam.app.community.service.BoardService;
import com.yedam.app.community.service.BoardVO;
import com.yedam.app.community.service.CommentsVO;
import com.yedam.app.member.service.MembVO;

@Controller
@RequestMapping("community")
public class BoardController {

	@Autowired
	BoardService boardService;

	// 게시판 목록 출력
	@GetMapping("boardList")
	public String getBoard(Model model, BoardVO vo) {
		model.addAttribute("startPage",1);
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

	// 글쓰기 페이지
	@GetMapping("addBoard")
	public String addBoardForm(Model model, BoardVO boardVo, HttpSession session) {
		model.addAttribute("boardInfo", boardVo);	
		System.out.println(boardService.getCtgr(boardVo.getCommonCd()));
		model.addAttribute("category", boardService.getCtgr(boardVo.getCommonCd()));
		return "community/insertBoard";
	}

	// 글쓰기 기능
	@PostMapping("addBoard")
	@ResponseBody
	public String addBoard(BoardVO vo) {
		vo.setMembNo(boardService.getMembNo(vo.getNick())); 
		System.out.println(vo);
		
		boolean result = boardService.insertBoard(vo);
		
		if (result) {
			return vo.getBoardNo();
		} else {
			return "fail";
		}
	}
	
	// 게시물 상세
	@GetMapping("boardDetail")
	public String boardDetail(Model model,BoardVO vo, HttpSession session) {
		boardService.increaseInquery(vo.getBoardNo());
		System.out.println(session.getAttribute("loggedInMember"));
		if(session.getAttribute("loggedInMember")!=null) {
			model.addAttribute("myInfo",session.getAttribute("loggedInMember"));
		}else {
			MembVO myInfo = new MembVO();
			myInfo.setMembNo("noLogin");
			model.addAttribute("myInfo",myInfo);
		}
		model.addAttribute("startPage",vo.getPage());
		model.addAttribute("boardCode", vo.getCommonCd().substring(0, 2));
		model.addAttribute("boardName", boardService.getBoardName(vo.getCommonCd().substring(0, 2)));
		vo = boardService.getBoardDetail(vo.getBoardNo());
		model.addAttribute("board",vo);
		model.addAttribute("member",boardService.getMembInfo(vo.getMembNo()));
		model.addAttribute("comments",boardService.getComments(vo.getBoardNo()));
		
		return "community/boardDetailForm";
	}
	
	// 게시물 추천버튼
	@PostMapping("addRcom")
	@ResponseBody
	public String addRcom(BoardVO vo) {
		if(boardService.addRcom(vo)) {
			return "success";
		}else {
			return "fail";
		}
	}
	
	// 게시물 비추천 버튼
	@PostMapping("addNrcom")
	@ResponseBody
	public String addNrcom(BoardVO vo) {
		if(boardService.addNrcom(vo)) {
			return "success";
		}else {
			return "fail";
		}
	}
	
	// 게시물 삭제
	@PostMapping("deleteBoard")
	@ResponseBody
	public String deleteBoard(BoardVO vo) {
		if(boardService.deleteBoard(vo)) {
			return "success";
		}else {
			return "fail";
		}
	}
	
	// 게시물 수정 페이지
	@GetMapping("modifyBoard")
	public String modifyBoardForm(Model model, BoardVO boardVo, HttpSession session) {
		model.addAttribute("boardInfo", boardVo);	
		model.addAttribute("category", boardService.getCtgr(boardVo.getCommonCd()));
		boardVo=boardService.getBoardDetail(boardVo.getBoardNo());
		System.out.println(boardVo);
		model.addAttribute("myBoard",boardVo);
		return "community/modifyBoard";
	}
	
	// 게시물 수정 기능
	@PostMapping("modifyBoard")
	@ResponseBody
	public String modifyBoard(BoardVO vo) {
		vo.setMembNo(boardService.getMembNo(vo.getNick()));
		System.out.println("aaaa"+vo);
		boolean result = boardService.modifyBoard(vo);
		
		if (result) {
			return vo.getBoardNo();
		} else {
			return "fail";
		}
	}
	
	// 댓글 추천 버튼
	@PostMapping("addCommentRcom")
	@ResponseBody
	public String addCommentRcom(CommentsVO vo) {
		if(boardService.addCommentRcom(vo)) {
			return "success";
		}else {
			return "fail";
		}
	}
	
	// 댓글 비추천 버튼
	@PostMapping("addCommentNrcom")
	@ResponseBody
	public String addCommentNrcom(CommentsVO vo) {
		if(boardService.addCommentNrcom(vo)) {
			return "success";
		}else {
			return "fail";
		}
	}
	
	// 댓글 작성
	@PostMapping("insertComment")
	@ResponseBody
	public CommentsVO insertComment(CommentsVO vo) {
		if(boardService.insertComment(vo)) {
			vo=boardService.getComment(vo.getCommNo());
			System.out.println(vo);
			return vo;
		}
		return null;
	}
	
	//댓글 삭제
	@PostMapping("deleteComment")
	@ResponseBody
	public String deleteComment(CommentsVO vo) {
		if(boardService.deleteComment(vo)) {
			return "success";
		}else {
			return "fail";
		}
	}
	
	// 대댓글 생성
	@PostMapping("insertSubComment")
	@ResponseBody
	public CommentsVO insertSubComment(CommentsVO vo) {
		if(boardService.insertSubComment(vo)) {
			vo=boardService.getComment(vo.getCommNo());
			return vo;
		}
		return null;
	}
}
