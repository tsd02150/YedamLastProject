package com.yedam.app.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.app.member.service.AddrVO;
import com.yedam.app.member.service.MembVO;
import com.yedam.app.member.service.MemberService;
import com.yedam.app.member.service.RegisterMail;

@Controller
@RequestMapping("member")
public class MemberController {
	@Autowired
	MemberService membService;
	
	@Autowired
	RegisterMail registerMail;

	
	@GetMapping("login")
	public String loginForm() {
		return "member/loginForm";
	}
	
	@GetMapping("mypage")
	public String myPageForm() {
		return "member/myPageInfo";
	}
	 
	@PostMapping("login")
	public String loginPost(MembVO membVO, Model model, HttpSession session) {
		//로그인 정보 비교
		MembVO loggedInMember = membService.loginCheck(membVO);
		System.out.println(loggedInMember);
		if (loggedInMember != null) {
	        // 로그인 성공한 경우
	        session.setAttribute("loggedInMember", loggedInMember); // 세션에 member 정보 저장
	        System.out.println("성공");
	        return "redirect:/"; // 로그인 후 메인 페이지로 리다이렉트
	    } else {
	        // 로그인 실패한 경우
	    	System.out.println("실패");
	        model.addAttribute("message", "아이디 또는 비밀번호가 틀렸습니다.");
	        return "redirect:login"; // 로그인 실패 시 다시 loginForm 호출
	    }
	}
	
	//회원가입 Form
	@GetMapping("join")
	public String joinForm() {
		return "member/joinMember";
	}
	
	//회원가입 - member insert
	@PostMapping("join")
	public String joinMemb(MembVO membVO, AddrVO addrVO) {
		if(addrVO.getZip().isEmpty()) { // 주소 입력하지 않았을 경우
			membService.signUpMemb(membVO);
			System.out.println(membVO);
			System.out.println(addrVO);
		} else if(!addrVO.getZip().isEmpty()){ // 주소 입력했을 경우
			membService.signUpMemb(membVO);
	        membVO.setMembNo(membService.getLastMembNo()); // membNo 값을 가져와서 membVO에 설정
	        addrVO.setMembNo(membVO.getMembNo()); // membNo 값을 addrVO에 설정
	        membService.insertAddr(addrVO);
			System.out.println(membVO);
			System.out.println(addrVO);
		} else {
			return "redirect:join";
		}
		return "redirect:login";			
	}
	
	//카카오 로그인
	@ResponseBody
	@RequestMapping("kakao") // 비밀번호는 지정해야되는데? 소셜로그인 테이블을 만든다 or 소셜로그인이면 비번 비교 어떻게 하지? 
	public String kakaoLogin(MembVO membVO, @RequestParam String nick, @RequestParam String id, HttpSession session) {
		if(membVO.getId()==null) { //처음 로그인하면 
			membVO.setMembNo(membService.selectMembNO());
			membVO.setNick(nick);
			membVO.setId(id);
			session.setAttribute("loggedInMember", membVO);
		}else {
			session.setAttribute("id", membVO.getId());
			session.setAttribute("nick", membVO.getNick());
			session.setAttribute("membNo", membVO.getMembNo());			
		}
		return "redirect:/";
	}
	
	@ResponseBody
	@RequestMapping("naver")
	public String naverLogin() {
		return null;
	}
	
	//닉네임 중복확인
	@ResponseBody
	@GetMapping("nickCheck")
	public int nicknameCheck(String nick, Model model) {
	    int result = membService.nickCheckBoolean(nick);
	    return result;
	}
	
	//아이디 중복확인
	@ResponseBody
	@GetMapping("idCheck")
	public int idCheck(String id, Model model) {
		int result = membService.idCheckBoolean(id);
		return result;
	}

	// 이메일 인증
	@PostMapping("mailConfirm")
	@ResponseBody
	String mailConfirm(@RequestParam("email") String email) throws Exception {

		String code = registerMail.sendSimpleMessage(email);
		System.out.println("인증코드 : " + code);
		return code;
	}
}
