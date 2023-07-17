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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yedam.app.mall.service.CouponVO;
import com.yedam.app.mall.service.OrderVO;
import com.yedam.app.mall.service.ProductVO;
import com.yedam.app.member.service.AddrVO;
import com.yedam.app.member.service.InterestVO;
import com.yedam.app.member.service.MembVO;
import com.yedam.app.member.service.MemberService;
import com.yedam.app.member.service.RegisterMail;
import com.yedam.app.security.service.UserService;
import com.yedam.app.security.service.UserVO;
import com.yedam.app.sms.service.MessageDTO;
import com.yedam.app.sms.service.SmsResponseDTO;
import com.yedam.app.sms.service.SmsService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("member")
@RequiredArgsConstructor
public class MemberController {
	private static final String UserVO = null;

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
	@GetMapping("callback")
	public String callback() {
		return "member/callback";
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
	String smsConfirm(@RequestParam("email") String email) throws Exception {
		//String email = membService.selectOneMemb(id).getEmail();
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
	public String tempPwdUpdate(UserVO userVO, MembVO membVO, HttpSession session, Model model) {
		MembVO loggedInMember = membService.selectOneMemb(membVO.getId());
		
		System.out.println(pwEncoder.matches(membVO.getPwd(),loggedInMember.getPwd()));
		if(pwEncoder.matches(membVO.getTempPwd(),loggedInMember.getPwd())) { //암호화된 비밀번호 맞는지 확인
			membVO.setPwd(pwEncoder.encode(membVO.getPwd()));//비밀번호 암호화
			int result = membService.updateTempPwd(membVO);
			if(result == 1) {
				session.setAttribute("loggedInMember", membVO);
				System.out.println("비밀번호 변경 성공");
				System.out.println(membVO);
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
	
	@ResponseBody
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
	
	//회원관리
	/*
	 * @ResponseBody
	 * 
	 * @PostMapping("mypageList") public MembVO mypageList(@RequestParam String
	 * id,Model model) { model.addAttribute("myinfo",membService.selectOneMemb(id));
	 * //System.out.println(membService.selectOneMemb(id)); return
	 * membService.selectOneMemb(id); }
	 */
	
	@GetMapping("mypage")
	public String myPageForm() {
		return "member/mypage";
	}
	
	@GetMapping("mypoint")
	public String myPointForm(HttpSession session,MembVO membVO, Model model) {
		UserVO mem = (UserVO) session.getAttribute("loggedInMember");
		MembVO member = membService.memberList(mem.getId());
		List<CouponVO> coupon = membService.mycoupon(mem.getMembNo());
		model.addAttribute("mypoint",member.getPoint());
		model.addAttribute("mycoupon",coupon);
		return "member/mypoint";
	}
	
	//주식현황
	@GetMapping("mystock")
	public String mystock() {
		return "member/mystock";
	}
	
	//설문조사
	@GetMapping("mysurvey")
	public String mysurvey() {
		return "member/mysurvey";
	}
	
	//주문내역
	@GetMapping("myorder")
	public String myorder() {
		return "member/myorder";
	}
	//결제 성공
	//paymentKey={PAYMENT_KEY}&orderId={ORDER_ID}&amount={AMOUNT}&paymentType={PAYMENT_TYPE}
	@GetMapping("paysuccess")
	public String paysuccess(String paymentKey, String orderId, int amount, String paymentType, Model model) {
		/*
		 * model.addAttribute("paymentKey", paymentKey); model.addAttribute("orderId",
		 * orderId); model.addAttribute("amount", amount);
		 * model.addAttribute("paymentType", paymentType);
		 */
		return "member/paysuccess";
	}
	//주문내역
	//?code={ERROR_CODE}&message={ERROR_MESSAGE}&orderId={ORDER_ID}
	@GetMapping("payfail")
	public String payfail(String code, String message, String orderId, Model model) {
		model.addAttribute("code", code);
		model.addAttribute("message", message);
		model.addAttribute("orderId", orderId);
		return "member/payfail";
	}
	
	//회원정보 상세보기
	@GetMapping("mypageIntro")
	public String mypageIntro() {
		return "member/mypageIntro";
	}
	@GetMapping("pointChargeForm")
	public String pointChargeForm(HttpSession session,MembVO membVO, Model model) {
		UserVO mem = (UserVO) session.getAttribute("loggedInMember");
		MembVO member = membService.memberList(mem.getId());
		model.addAttribute("mypoint",member.getPoint());
		return "member/pointChargeForm";
	}
	
	@ResponseBody
	@PostMapping("interestList")
	public List<MembVO> interestList(@RequestParam String id, Model model, MembVO membVO) {
		//UserVO meminfo = (UserVO)session.getAttribute("loggedInMember");
		membVO.setId(id);
		List<MembVO> interestList = membService.myinterestList(membVO);
		//model.addAttribute("interestList",interestList);
		System.out.println(interestList);
		return interestList;
	}
	
	@ResponseBody
	@PostMapping("stockList")
	public List<MembVO> myPageInfo(@RequestParam String id, Model model, MembVO membVO) {
		//UserVO meminfo = (UserVO)session.getAttribute("loggedInMember");
		membVO.setId(id);
		List<MembVO> stockList = membService.myStockList(membVO);
		//model.addAttribute("stocklist",stockList);
		System.out.println(stockList);
		return stockList;
	}
	
	//@ResponseBody
	@GetMapping("mypageInfo")
	public String mypageInfo(HttpSession session, Model model, MembVO membVO) {
		UserVO mem = (UserVO) session.getAttribute("loggedInMember");
		String id = mem.getId();
		List<String> list = membService.membListInfo(id);
		MembVO member = membService.memberList(id);
		model.addAttribute("membList",list);
		model.addAttribute("member",member);
		return "member/mypageInfo";
	}
	
	//주문, 배송내역 리스트 
	@ResponseBody
	@PostMapping("mypageOrderList")
	public List<OrderVO> mypageOrderList(@RequestParam String id, @RequestParam String orderSt) {
	  OrderVO orderVO = new OrderVO();
	  orderVO.setId(id);
	  orderVO.setOrderSt(orderSt);
	  //System.out.println(membService.mypageOrderList(orderVO));
	  return membService.mypageOrderList(orderVO);
	}
	
	//상품 이름, 이미지 리스트
	@ResponseBody
	@GetMapping("mypagePrdtList")
	public List<ProductVO> mypagePrdtList(ProductVO prdtVO) {
		return membService.mypagePrdtList(prdtVO);
	}
	
	//회원정보 수정
	//@ResponseBody
	@PostMapping("updateMemberInfo")
	public String updateMemberInfo(@RequestParam String nick, @RequestParam String tel,
	        @RequestParam String pwd, @RequestParam String id, @RequestParam String email,MembVO membVO, Model model, HttpSession session) {
	    MembVO mem = membService.memberList(id);
	    membVO.setId(id);
	    membVO.setNick(nick);
	    membVO.setEmail(email);
	    membVO.setTel(tel);
	    if(pwd == mem.getPwd()) {
	    	if (mem.getTempPwd() == null) {
	    		membVO.setPwd(pwd);
	    	} else {
	    		mem.setTempPwd(pwd);
	    		mem.setPwd(pwd);
	    	}
	    }else {
	    	if (mem.getTempPwd() == null) {
		        membVO.setPwd(pwEncoder.encode(pwd)); // 비밀번호 암호화
		    } else {
		        membVO.setPwd(pwEncoder.encode(pwd));
		        membVO.setTempPwd(pwEncoder.encode(pwd));
		    }
	    }
	    
	    membService.updateMemberInfo(membVO);
	    System.out.println(membVO);
	    //수정한 정보 다시 세션에 저장
	    MembVO list = membService.memberList(id);
	    UserVO loggedInMember = new UserVO();
	    loggedInMember.setId(membVO.getId());
	    loggedInMember.setNick(membVO.getNick());
	    loggedInMember.setEmail(membVO.getEmail());
	    loggedInMember.setTel(membVO.getTel());
	    loggedInMember.setPwd(membVO.getPwd());
	    loggedInMember.setTempPwd(membVO.getTempPwd());
	    
		model.addAttribute("membList",list);
		
	    return "redirect:mypageInfo";
	}
	
	//주소 수정
	@ResponseBody
	@PostMapping("updateMemberAddr")
	public AddrVO updateMemberAddr(@RequestParam String addrNo,@RequestParam String membNo,@RequestParam String zip,@RequestParam String addr,@RequestParam String detaAddr, AddrVO addrVO) {
		addrVO.setAddrNo(addrNo);
		addrVO.setZip(zip);
		addrVO.setAddr(addr);
		addrVO.setDetaAddr(detaAddr);
		addrVO.setMembNo(membNo);
		membService.updateMemberAddr(addrVO);
		System.out.println(membService.updateMemberAddr(addrVO));
		return addrVO;
	}
	
	//주소 추가
	@ResponseBody
	@PostMapping("insertMemberAddr")
	public AddrVO insertMemberAddr(@RequestParam String zip,@RequestParam String addr,@RequestParam String detaAddr, AddrVO addrVO) {
		addrVO.setZip(zip);
		addrVO.setAddr(addr);
		addrVO.setDetaAddr(detaAddr);
		membService.insertAddr(addrVO);
		System.out.println("!!! 정 보 수 정!!!!");
		System.out.println(membService.insertAddr(addrVO));
		System.out.println("!!!!!!!");
		return addrVO;
	}
	
	@ResponseBody
	@PostMapping("selectOneMembInfo")
	public List<MembVO> selectOneMembInfo(@RequestParam String id, MembVO membVO,Model model) {
		membVO.setId(id);
		model.addAttribute("info", membService.selectOneMemb2(membVO));
		System.out.println(membService.selectOneMemb2(membVO));
		return membService.selectOneMemb2(membVO);
	}
	
	@ResponseBody
	@PostMapping("mypageIntroCheckPwd")
	public String mypageIntroCheckPwd(@RequestParam String pwd, @RequestParam String id, MembVO membVO, Model model) {
		membVO = membService.memberList(id);
		System.out.println(pwd);
		System.out.println(membVO.getPwd());
		System.out.println(pwEncoder.matches(pwd, membVO.getPwd()));
		if (pwEncoder.matches(pwd, membVO.getPwd())) {
			return "success";
		} else {
			//model.addAttribute("fail", "비밀번호를 다시 입력하세요");
			return "fail";
		}
	}
}
