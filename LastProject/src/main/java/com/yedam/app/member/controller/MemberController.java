package com.yedam.app.member.controller;

import javax.servlet.http.HttpSession;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.member.service.impl.MembVO;
import com.yedam.app.member.service.impl.MemberService;


//@MapperScan(basePackages = "com.yedam.app.**.mapper")
@Controller
//@RequestMapping("/member")
public class MemberController {
	@Autowired
	MemberService membService;
	
	@GetMapping("main")
	public String mainForm() {
		return "main/layout";
	}
	
	@GetMapping("userLogin")
	public String loginForm() {
		return "member/loginForm2";
	}
	
	@PostMapping("/userLogin")
	public String loginPost(MembVO membVO, Model model, HttpSession session) {
		//로그인 정보 비교
		MembVO loggedInMember = membService.loginCheck(membVO);
		System.out.println(loggedInMember);
		if (loggedInMember != null) {
	        // 로그인 성공한 경우
	        session.setAttribute("loggedInMember", loggedInMember); // 세션에 member 정보 저장
	        System.out.println("성공");
	        return "redirect:main"; // 로그인 후 메인 페이지로 리다이렉트
	    } else {
	        // 로그인 실패한 경우
	    	System.out.println("실패");
	        model.addAttribute("message", "아이디 또는 비밀번호가 틀렸습니다.");
	        return "redirect:userLogin"; // 로그인 실패 시 다시 loginForm 호출
	    }
	}
	
	//회원가입 Form
	@GetMapping("join")
	public String joinForm() {
		return"member/joinMember";
	}
	
	//닉네임 중복확인
	@ResponseBody
	@GetMapping("nickCheck")
	public int nicknameCheck(String nick, Model model) {
		int result = membService.nickCheckBoolean(nick);
		return result;
	}
	
	
	

}
