package com.yedam.app.member.controller;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yedam.app.member.service.AddrVO;
import com.yedam.app.member.service.InterestVO;
import com.yedam.app.member.service.MembVO;
import com.yedam.app.member.service.MemberService;
import com.yedam.app.member.service.RegisterMail;
import com.yedam.app.security.service.UserService;
import com.yedam.app.sms.service.MessageDTO;
import com.yedam.app.sms.service.SmsResponseDTO;
import com.yedam.app.sms.service.SmsService;

@Controller
@RequestMapping("member")
public class MemberController {
	@Autowired
	MemberService membService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	RegisterMail registerMail;

	@Autowired
	SmsService smsService;
	
	@Autowired
	private PasswordEncoder pwEncoder;
	
	//회원관리
	@GetMapping("mypage")
	public String myPageForm() {
		return "member/mypage";
	}
	
	//로그아웃
	@GetMapping("logout")
	public String logout(HttpSession session) {
        session.invalidate();
        System.out.println("로그아웃");
        return "redirect:/";
	}
	
	//로그인 페이지
	/*@PostMapping("mainLogin")
	public String loginPost(@RequestParam String id, @RequestParam String pwd, MembVO membVO, Model model, HttpSession session) {
		System.out.println(membService.selectOneMemb(id).getPwd());
		System.out.println(membVO.getPwd());
		if(pwEncoder.matches(membService.selectOneMemb(id).getPwd(), membVO.getPwd())) {
			MembVO loggedInMember = membService.mainLoginCheck(membVO);
			System.out.println(loggedInMember);
			if (loggedInMember.getTempPwd() == null) {
				// 로그인 성공한 경우
				session.setAttribute("loggedInMember", loggedInMember); // 세션에 member 정보 저장
				System.out.println("로그인성공");
				return "redirect:/"; // 로그인 후 메인 페이지로 리다이렉트
			} else{
				model.addAttribute("id", loggedInMember.getId());
				session.setAttribute("loggedInMember", loggedInMember); // 세션에 member 정보 저장
				System.out.println("로그인성공(임시비밀번호)");
				return "member/tempPwdUpdate";
			}
		}else {
	        // 로그인 실패한 경우
	    	System.out.println("일반 로그인 실패");
	    	model.addAttribute("message", "아이디 또는 비밀번호가 틀렸습니다."); // 세션에 member 정보 저장	    	
	        return "redirect:/"; // 로그인 실패 시 다시 loginForm 호출
	    }
	}*/
	
	//회원가입 Form
	@GetMapping("join")
	public String joinForm() {
		return "member/joinMember";
	}
	
	//회원가입 - member insert
	@PostMapping("join")
	public String joinMemb(MembVO membVO, AddrVO addrVO, Model model) {
        
		if(addrVO.getZip().isEmpty()) { // 주소 입력하지 않았을 경우
			membVO.setPwd(pwEncoder.encode(membVO.getPwd()));//비밀번호 암호화
			membService.signUpMemb(membVO);
			System.out.println(membVO);
			System.out.println(addrVO);
		} else if(!addrVO.getZip().isEmpty()){ // 주소 입력했을 경우
			membVO.setPwd(pwEncoder.encode(membVO.getPwd()));//비밀번호 암호화
			membService.signUpMemb(membVO);
	        membVO.setMembNo(membService.getLastMembNo()); // membNo 값을 가져와서 membVO에 설정
	        addrVO.setMembNo(membVO.getMembNo()); // membNo 값을 addrVO에 설정
	        membService.insertAddr(addrVO);
			System.out.println(membVO);
			System.out.println(addrVO);
		} else {
			return "redirect:join";
		}
		model.addAttribute("joinInfoNo", membVO.getMembNo());
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
		membVO.setPwd(pwEncoder.encode(code));
		membVO.setId(id);
		membVO.setTempPwd(pwEncoder.encode(code));
		membService.updatePwd(membVO);
		
		return code;
	}
	
	//인증번호 발송
	@ResponseBody
	@PostMapping("sendSms")
	public SmsResponseDTO findId(MessageDTO messageDto) throws JsonProcessingException, RestClientException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException, URISyntaxException {
	    SmsResponseDTO response = smsService.sendSms(messageDto);
	    System.out.println("문자인증"+response);
	    return response;
	}
	//model.addAttribute("response", response);
	
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
		MembVO loggedInMember = membService.selectOneMemb(membVO.getId());
		System.out.println("=========>");
		System.out.println(loggedInMember);
		System.out.println(membVO.getTempPwd());
		System.out.println(loggedInMember.getPwd());
		
		System.out.println(pwEncoder.matches(membVO.getPwd(),loggedInMember.getPwd()));
		if(pwEncoder.matches(membVO.getTempPwd(),loggedInMember.getPwd())) { //암호화된 비밀번호 맞는지 확인
			membVO.setPwd(pwEncoder.encode(membVO.getPwd()));//비밀번호 암호화
			int result = membService.updateTempPwd(membVO);
			if(result == 1) {
				session.setAttribute("loggedInMember", loggedInMember);
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
	public List<InterestVO> interestItemList(){
//		System.out.println(membService.myItemCheck());
		return membService.myItemCheck();
	}
	
	//관심종목 리스트 선택 페이지
	@GetMapping("myItemCheckForm")
	public String interestItemPage(Model model, InterestVO commonVO) {
//		model.addAttribute("listctgr", membService.myItemCheck());
		return "member/myItemCheckForm";
	}
	
	//관심종목 선택 insert
	@ResponseBody
	@PostMapping("insertInterestItem")
	public int insertInterestItem(@RequestParam String membNo, @RequestParam String itemNo, MembVO membVO){
		membVO.setMembNo(membNo);
		membVO.setItemNo(itemNo);
		System.out.println(membVO);
		int test =membService.insertInterestItem(membVO);
		System.out.println(membVO);
		return test;
	}
	
	//id찾기 이름-연락처 비교
	@ResponseBody
	@GetMapping("findIdCheck")
	public MembVO findIdSelectCheck(MembVO membVO,String nm, String tel, Model model) {
		membVO.setNm(nm);
		membVO.setTel(tel);
		MembVO membInfo = membService.findIdSelect(membVO);
		model.addAttribute("membInfo", membInfo);
		return membInfo;
	}
	
	//@ResponseBody
	@PostMapping("findIdSuccess")
	public String findIdSuccessPage(@RequestParam String id, Model model) {
		model.addAttribute("id", id);
		return "member/findIdSuccess";
	}
	
	//tel 정보 비교
	@ResponseBody
	@PostMapping("getMemberTel")
	public MembVO getMemberTel(@RequestParam String tel, @RequestParam String nm, MembVO membVO, Model model) {
		membVO.setTel(tel);
		membVO.setNm(nm);
		MembVO result = membService.getMemberTel(membVO);
		return result;
	}
}
