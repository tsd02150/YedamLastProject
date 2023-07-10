package com.yedam.app.member.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yedam.app.member.service.AddrVO;
import com.yedam.app.member.service.CommonVO;
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

	
	//회원관리
	@GetMapping("mypage")
	public String myPageForm() {
		return "member/myPageInfo";
	}
	
	//로그아웃
	public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
	}
	
	//로그인 페이지
	@GetMapping("login")
	public String loginForm() {
		return "member/loginForm";
	}
	
	@GetMapping("loginpwd")
	public String loginFormPwd() {
		return "member/loginFormPwd";
	}
	
	
	@PostMapping("login")
	public String loginPost(MembVO membVO, Model model, HttpSession session) {
		//로그인 정보 비교
		MembVO loggedInMember = membService.loginCheck(membVO);
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
	
	@PostMapping("loginpwd")
	public String loginPostPwd(MembVO membVO, Model model) {
		MembVO loggedInMember = membService.loginCheckPwd(membVO);
		if (loggedInMember != null) {
	        // 로그인 성공한 경우
	        model.addAttribute("id", membVO.getId()); // 세션에 member 정보 저장
	        System.out.println("id : "+membVO.getId());
	        System.out.println("로그인성공(임시비밀번호)");
	        return "member/tempPwdUpdate"; // 로그인 후 메인 페이지로 리다이렉트
	    } else {
	        // 로그인 실패한 경우
	    	System.out.println("실패");
	        model.addAttribute("message", "아이디 또는 비밀번호가 틀렸습니다.");
	        return "redirect:loginpwd"; // 로그인 실패 시 다시 loginForm 호출
	    }
	}
	    
	//회원가입 Form
	@GetMapping("join")
	public String joinForm() {
		return "member/joinMember";
	}
	
	//회원가입 - member insert
	@PostMapping("join")
	public String joinMemb(MembVO membVO, AddrVO addrVO, Model model) {
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
		model.addAttribute("joinInfoId", membVO.getId());
		return "member/myItemCheckForm";			
	}
	
	//카카오 로그인
	@ResponseBody
	@RequestMapping("kakao")
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

	//아이디 찾기 페이지
	@GetMapping("findid")
	public String findIdForm() {
		return "member/findIdForm";
	}
		
	//비밀번호 찾기 페이지
	@GetMapping("findpwd")
	public String findPwdForm() {
		return "member/findPwdForm";
	}
		 
	// 이메일 인증번호 발송
	@PostMapping("mailConfirm")
	@ResponseBody
	String mailConfirm(@RequestParam("id") String id) throws Exception {
		String email = membService.selectOneMemb(id).getEmail();
		System.out.println(email);
		String code = registerMail.sendSimpleMessage(email);
		System.out.println("인증코드 : " + code);
		return code;
	}
	
	@PostMapping("smsConfirm")
	@ResponseBody
	String smsConfirm(@RequestParam("id") String id) throws Exception {
		String email = membService.selectOneMemb(id).getEmail();
		System.out.println(email);
		String code = registerMail.sendSimpleMessage(email);
		System.out.println("인증코드 : " + code);
		return code;
	}

	// 임시비밀번호 발급 - 이메일
	@ResponseBody
	@PostMapping("mailConfirmPwd")
	public String findPwdEmail(@RequestParam("id") String id, MembVO membVO) throws Exception {
		String email = membService.selectOneMemb(id).getEmail();
		String code = registerMail.sendTempPwdMessage(email); 
		System.out.println("임시비밀번호 : " + code);
		
		//기존 비밀번호 임시 비밀번호로 수정.
		membVO.setPwd(code);
		membVO.setId(id);
		membVO.setTempPwd(code);
		membService.updatePwd(membVO);
		
		return code;
	}
	
	//임시 비밀번호 발급 후 페이지 호출
//	@ResponseBody
	@PostMapping("tempPwdSuccess")
	public String renewPwd(@RequestParam("id") String id, Model model) {
		System.out.println(id);
		String email = membService.selectOneMemb(id).getEmail();
		System.out.println(email);
		model.addAttribute("email", email);
		return "member/tempPwdSuccess";
	}
	
	//임시 비밀번호 변경
	@GetMapping("tempPwdUpdate")
	public String tempPwdUpdate() {
		return "member/tempPwdUpdate";
	}
	
	@PostMapping("tempPwdUpdate")
	public String tempPwdUpdate(MembVO membVO, HttpSession session, Model model) {
		if(membService.loginCheckPwd(membVO) !=null) {
			int result = membService.updateTempPwd(membVO);
			
			if(result == 1) {
				session.setAttribute("loggedInMember", membService.loginCheck(membVO));
				System.out.println("비밀번호 변경 성공");
				return "redirect:/";							
			}else {
				model.addAttribute("id",membVO.getId());
				System.out.println("비밀번호 변경 실패");
				return "member/tempPwdUpdate";
			}
		} else {
			model.addAttribute("id",membVO.getId());
			model.addAttribute("message","비밀번호가 일치하지 않습니다. 다시 확인해주세요");
			return "member/tempPwdUpdate";
		}
	}
	
	//관심종목 리스트 정보
	@ResponseBody
	@GetMapping("myItemCheck")
	public List<CommonVO> interestItemList(){
		return membService.myItemCheck();
	}
	
	//관심종목 리스트 선택 페이지
	@GetMapping("myItemCheckForm")
	public String interestItemPage(Model model, CommonVO commonVO) {
//		model.addAttribute("listctgr", membService.myItemCheck());
		return "member/myItemCheckForm";
	}
	
	//인증번호 sms 발송
	@GetMapping("sms")
	public String sendSMS() {
		return "";
	}
	
	//id찾기 이름-연락처 비교
	@ResponseBody
	@GetMapping("findIdCheck")
	public List<MembVO> findIdSelectCheck(MembVO membVO,String nm, String tel, Model model) {
		membVO.setNm(nm);
		membVO.setTel(tel);
		System.out.println(membService.findIdSelect(membVO));
		return membService.findIdSelect(membVO);
	}
	
}
