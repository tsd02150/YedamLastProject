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

import com.yedam.app.community.service.FaqVO;
import com.yedam.app.community.service.QuestionService;
import com.yedam.app.community.service.QuestionVO;
import com.yedam.app.member.service.MembVO;
import com.yedam.app.security.service.UserVO;

@Controller
@RequestMapping("community")
public class QuestionController {
	@Autowired
	QuestionService questionService;
	
	@GetMapping("questionList")
	public String questionList(Model model,HttpSession session) {
		model.addAttribute("startPage",1);
		return "community/questionList";
	}
	
	@PostMapping("getFaqList")
	@ResponseBody
	public List<FaqVO> getFaqList(FaqVO vo) {
		List<FaqVO> list=questionService.getFaqList(vo);
		return list;
	}
	
	@PostMapping("getFaqCount")
	@ResponseBody
	public int getFaqCount(FaqVO vo) {
		return questionService.getFaqCount(vo);
	}
	@PostMapping("getQnaList")
	@ResponseBody
	public List<QuestionVO> getQnaList(QuestionVO vo) {
		List<QuestionVO> list=questionService.getQnaList(vo);
		return list;
	}
	
	@PostMapping("getQnaCount")
	@ResponseBody
	public int getQnaCount(QuestionVO vo) {
		return questionService.getQnaCount(vo);
	}
	
	@GetMapping("addQna")
	public String insertQnaForm(HttpSession session) {
		return "community/insertQna";
	}
	
	@PostMapping("addQna")
	@ResponseBody
	public String insertQna(QuestionVO vo,HttpSession session) {
		vo.setMembNo(((UserVO)session.getAttribute("loggedInMember")).getMembNo());
		if(questionService.insertQna(vo)) {
			return "success";
		}else {
			return "fail";			
		}
	}
	 
	@GetMapping("qnaDetail")
	public String qnaDetailForm(Model model, QuestionVO vo,HttpSession session) {
		questionService.increaseInq(vo.getQstNo());
		if(session.getAttribute("loggedInMember")!=null) {
			model.addAttribute("myInfo",session.getAttribute("loggedInMember"));
		}else {
			MembVO myInfo = new MembVO();
			myInfo.setMembNo("noLogin");
			model.addAttribute("myInfo",myInfo);
		}
		model.addAttribute("qnaInfo",questionService.getQnaDetail(vo.getQstNo()));
		return "community/qnaDetail";
	}
	
	@PostMapping("deleteQna")
	@ResponseBody
	public String deleteQna(QuestionVO vo) {
		if(questionService.deleteQna(vo.getQstNo())) {
			return "success";
		}else {
			return "fail";
		}
	}
	
	@GetMapping("modifyQna")
	public String modifyQnaForm(Model model,QuestionVO vo) {
		vo = questionService.getQnaDetail(vo.getQstNo());
		model.addAttribute("qnaInfo",vo);
		return "community/modifyQna";
	}
}
