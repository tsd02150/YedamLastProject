package com.yedam.app.community.controller;

import java.util.List;

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

@Controller
@RequestMapping("community")
public class QuestionController {
	@Autowired
	QuestionService questionService;
	
	@GetMapping("questionList")
	public String qustionList(Model model) {
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
		System.out.println(vo);
		List<QuestionVO> list=questionService.getQnaList(vo);
		System.out.println(list);
		return list;
	}
	
	@PostMapping("getQnaCount")
	@ResponseBody
	public int getQnaCount(QuestionVO vo) {
		return questionService.getQnaCount(vo);
	}
}
