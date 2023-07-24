package com.yedam.app.community.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.community.service.BoardService;
import com.yedam.app.community.service.BoardVO;
import com.yedam.app.community.service.CommentsVO;
import com.yedam.app.member.service.InterestVO;
import com.yedam.app.member.service.MembVO;

@Controller
@RequestMapping("community")
public class BoardController {

	@Autowired
	BoardService boardService;

	// 게시판 목록 출력
	@GetMapping("boardList")
	public String getBoard(Model model, BoardVO vo) {
		// 첫 화면 페이징 1
		model.addAttribute("startPage",1);
		// 게시판 별 정보를 가져오기 위한 공통 코드 C1 : 자유게시판    C2 : 종목게시판
		model.addAttribute("boardCode", vo.getCommonCd().substring(0, 2));
		// 게시판 별 이름
		model.addAttribute("boardName", boardService.getBoardName(vo.getCommonCd().substring(0, 2)));
		
		return "community/boardList";
	}
	
	// 글쓰기 페이지
	@GetMapping("addBoard")
	public String addBoardForm(Model model, BoardVO boardVo, HttpSession session) {
		// 해당 게시판 공통코드 정보 담고잇는 객체
		model.addAttribute("boardInfo", boardVo);	
		// 해당 게시판의 카테고리 영역 정보
		model.addAttribute("category", boardService.getCtgr(boardVo.getCommonCd()));
		
		return "community/insertBoard";
	}
	
	// 게시물 상세 페이지
	@GetMapping("boardDetail")
	public String boardDetail(Model model,BoardVO vo, HttpSession session) {
		// 상세페이지 진입 시 조회수 1 증가
		boardService.increaseInquery(vo.getBoardNo());
		
		// 로그인 여부 정보
		if(session.getAttribute("loggedInMember")!=null) {
			model.addAttribute("myInfo",session.getAttribute("loggedInMember"));
		}else {
			MembVO myInfo = new MembVO();
			myInfo.setMembNo("noLogin");
			model.addAttribute("myInfo",myInfo);
		}
		// 첫 화면 페이징 1
		model.addAttribute("startPage",vo.getPage());
		// 공통 코드
		model.addAttribute("boardCode", vo.getCommonCd().substring(0, 2));
		// 게시판 이름
		model.addAttribute("boardName", boardService.getBoardName(vo.getCommonCd().substring(0, 2)));
		// 해당 게시판 정보
		vo = boardService.getBoardDetail(vo.getBoardNo());
		model.addAttribute("board",vo);
		// 게시물 작성자 정보
		model.addAttribute("member",boardService.getMembInfo(vo.getMembNo()));
		System.out.println(boardService.getMembInfo(vo.getMembNo()));
		// 게시물 댓글 정보
		model.addAttribute("comments",boardService.getComments(vo.getBoardNo()));
		// 게시물 첨부파일 정보
		model.addAttribute("attachList",boardService.getAttachList(vo.getBoardNo()));
		
		return "community/boardDetail";
	}
	
	// 게시물 수정 페이지
	@GetMapping("modifyBoard")
	public String modifyBoardForm(Model model, BoardVO boardVo, HttpSession session) {
		// 게시물 번호, 공통코드, 게시판 종류 정보
		model.addAttribute("boardInfo", boardVo);	
		// 게시판 종류 별 게시물 카테고리 정보
		model.addAttribute("category", boardService.getCtgr(boardVo.getCommonCd()));
		// 해당 게시물 정보
		boardVo=boardService.getBoardDetail(boardVo.getBoardNo());
		model.addAttribute("myBoard",boardVo);
		
		return "community/modifyBoard";
	}

	// 게시판 전체 목록 가져오기 기능
	@PostMapping("getBoardList")
	@ResponseBody
	public List<BoardVO> getBoardList(BoardVO vo) {
		return boardService.getBoardList(vo);
	}

	// 페이징을 위한 게시물 개수
	@PostMapping("getBoardCount")
	@ResponseBody
	public int getBoardCount(BoardVO vo) {
		return boardService.getBoardCount(vo);
	}

	// 글쓰기 기능
	@PostMapping("addBoard")
	@ResponseBody
	public String addBoard(BoardVO vo) {
		// 닉네임 정보로 멤버 번호 추가
		vo.setMembNo(boardService.getMembNo(vo.getNick())); 
		
		if (boardService.insertBoard(vo)) {
			return vo.getBoardNo();
		} else {
			return "fail";
		}
	}
	
	// 게시물 추천버튼 클릭 기능
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
	

	
	// 게시물 수정 기능
	@PostMapping("modifyBoard")
	@ResponseBody
	public String modifyBoard(BoardVO vo) {
		vo.setMembNo(boardService.getMembNo(vo.getNick()));
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
	
	// 회원정보 모달창
	@PostMapping("getMemberInfo")
	@ResponseBody
	public Map<String, Object> getMemberInfo(MembVO vo){
		vo=boardService.getMembInfo(vo.getMembNo());
		List<InterestVO> list = boardService.getInerestStockInfo(vo.getMembNo());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("membInfo", vo);
		map.put("stockList", list);
		
		return map;
	}
}
