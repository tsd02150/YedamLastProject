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
import com.yedam.app.mall.service.CouponVO;
import com.yedam.app.mall.service.OrderVO;
import com.yedam.app.mall.service.ProductVO;
import com.yedam.app.member.service.AddrVO;
import com.yedam.app.member.service.BuyOrderVO;
import com.yedam.app.member.service.ChargeVO;
import com.yedam.app.member.service.DealVO;
import com.yedam.app.member.service.InterestVO;
import com.yedam.app.member.service.MembVO;
import com.yedam.app.member.service.MemberService;
import com.yedam.app.member.service.PossVO;
import com.yedam.app.member.service.RegisterMail;
import com.yedam.app.member.service.SellOrderVO;
import com.yedam.app.member.service.SurveyVO;
import com.yedam.app.security.service.UserService;
import com.yedam.app.security.service.UserVO;
import com.yedam.app.sms.service.MessageDTO;
import com.yedam.app.sms.service.SmsResponseDTO;
import com.yedam.app.sms.service.SmsService;
import com.yedam.app.stock.service.StockVO;

import lombok.RequiredArgsConstructor;

//김미향 230707 회원관리 컨트롤러.
@Controller
@RequestMapping("member")
@RequiredArgsConstructor
public class MemberController {
	
	//private static final String UserVO = null;
	
	//인증코드 저장
	//private static final Map<String, Object> codeList; //키, id(session)

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
	/*
	 * @GetMapping("logout") public String logout(HttpSession session) {
	 * session.invalidate(); System.out.println("로그아웃"); return "redirect:/"; }
	 */
	
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
        
		//회원가입 처리
		membVO.setPwd(pwEncoder.encode(membVO.getPwd()));//비밀번호 암호화
		membService.signUpMemb(membVO);
		
		//// 주소 입력했을 경우
		if(!addrVO.getZip().isEmpty()){ 
	        membVO.setMembNo(membService.getLastMembNo()); // membNo 값을 가져와서 membVO에 설정
	        addrVO.setMembNo(membVO.getMembNo()); // membNo 값을 addrVO에 설정
	        membService.insertAddr(addrVO);
		}
		
		//관심등록 페이지로 이동.
		model.addAttribute("joinInfoNo", membVO.getMembNo());
		return "member/myItemCheckForm";			
	}
	
	//카카오 로그인 -
	@ResponseBody
	@RequestMapping("kakao")
	public String kakaoLogin(MembVO membVO, @RequestParam String code, @RequestParam String id, HttpSession session) {
		
		if(membVO.getId()==null) { //처음 로그인하면 
			membVO.setMembNo(membService.selectMembNO());
			//membVO.setNick(nick);
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
	
	//임시비밀번호 변경 처리.
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
		model.addAttribute("startPage",1);
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
	@GetMapping("paysuccess")
	public String paysuccess(@RequestParam(value="amount") int amount, HttpSession session, ChargeVO chargeVO, Model model, MembVO membVO) {
		UserVO mem = (UserVO) session.getAttribute("loggedInMember");
		chargeVO.setMembNo(mem.getMembNo());
		chargeVO.setChagPrc(amount);
		membVO.setId(mem.getId());
		
		//결제 정보 저장, 등록		//포인트적립
		membService.insertCharge(chargeVO, membVO);

		//수정된 정보 세션에 다시 저장.
	    mem.setPoint(membVO.getPoint());
	    session.setAttribute("loggedInMember", mem);
		model.addAttribute("membList", membVO); //update된 회원 정보
		return "member/paysuccess";
	}
	
	//주문내역
	@GetMapping("payfail")
	public String payfail() {
		return "member/payfail";
	}
	
	//회원정보 상세보기
	@GetMapping("mypageIntro")
	public String mypageIntro() {
		return "member/mypageIntro";
	}
	
	//포인트 충전 페이지.
	@GetMapping("pointChargeForm")
	public String pointChargeForm(HttpSession session,MembVO membVO, Model model) {
		UserVO mem = (UserVO) session.getAttribute("loggedInMember");
		MembVO member = membService.memberList(mem.getId());
		model.addAttribute("mypoint",member.getPoint());
		return "member/pointChargeForm";
	}
	
	//내 관심종목 리스트
	@ResponseBody
	@PostMapping("interestList")
	public List<StockVO> interestList(@RequestParam String membNo, StockVO stockVO) {
		//UserVO mem = (UserVO)session.getAttribute("loggedInMember");
		List<StockVO> interestList = membService.interestList(membNo);
		//System.out.println(interestList);
		return interestList;
	}
	
	//내 보유 주식 리스트
	/*
	 * @ResponseBody
	 * 
	 * @PostMapping("myStockList") public List<StockVO> myStockList(@RequestParam
	 * String membNo) { List<StockVO> stockList = membService.myStockList(membNo);
	 * System.out.println(stockList); return stockList; }
	 */
	
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
	  return membService.mypageOrderList(orderVO);
	}
	
	//상품 이름, 이미지 리스트
	@ResponseBody
	@GetMapping("mypagePrdtList")
	public List<ProductVO> mypagePrdtList(ProductVO prdtVO) {
		return membService.mypagePrdtList(prdtVO);
	}
	
	//회원정보 수정
	@PostMapping("updateMemberInfo")
	public String updateMemberInfo(@RequestParam String nick, @RequestParam String tel,
	                               @RequestParam String pwd, @RequestParam String id, @RequestParam String email,
	                               MembVO membVO, Model model, HttpSession session) {
	    MembVO mem = membService.memberList(id);
	    membVO.setId(id);
	    membVO.setNick(nick);
	    membVO.setEmail(email);
	    membVO.setTel(tel);
	    membVO.setPoint(mem.getPoint());
	    
	    if (pwd != null && !pwd.equals("")) { // 비밀번호 변경 시 
	    	membVO.setPwd(pwEncoder.encode(pwd));
	    	membVO.setTempPwd("");
	    }
	    
	    membService.updateMemberInfo(membVO);

	    //수정한 정보 다시 세션에 저장
	    MembVO list = membService.memberList(id);
	    System.out.println("++++++++++++++++++++");
	    System.out.println(list);
	    UserVO loggedInMember = new UserVO();
	    loggedInMember.setId(list.getId());
	    loggedInMember.setNick(list.getNick());
	    loggedInMember.setEmail(list.getEmail());
	    loggedInMember.setTel(list.getTel());
	    loggedInMember.setPwd(list.getPwd());
	    loggedInMember.setTempPwd(list.getTempPwd());
	    loggedInMember.setPoint(list.getPoint());
	    loggedInMember.setMembNo(list.getMembNo());
	    loggedInMember.setJoinDt(list.getJoinDt());
	    session.setAttribute("loggedInMember", loggedInMember);
	    model.addAttribute("membList", list);

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
		System.out.println(membService.insertAddr(addrVO));
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
		if (pwEncoder.matches(pwd, membVO.getPwd())) {
			return "success";
		} else {
			//model.addAttribute("fail", "비밀번호를 다시 입력하세요");
			return "fail";
		}
	}
	
	//
	@GetMapping("mystockInfo")
	public String mystockInfo(HttpSession session, Model model) {
		UserVO mem = (UserVO) session.getAttribute("loggedInMember");
		
		List<PossVO> possstockList = membService.myPossStockList(mem.getMembNo());
		model.addAttribute("possstockList", possstockList);
		
		int sumNowPrc = possstockList.stream().mapToInt(PossVO::getNowPrc).sum();
	    int sumTradePrc = possstockList.stream().mapToInt(PossVO::getTradePrc).sum();
	    double raise = (sumTradePrc / sumNowPrc) * 100;

	    model.addAttribute("sumNowPrc", sumNowPrc);
	    model.addAttribute("sumTradePrc", sumTradePrc);
	    model.addAttribute("raise", raise);
	    
		return "member/mystockInfo";
	}
	
	//매도 주문 현황
	@ResponseBody
	@PostMapping("sellOrderList")
	public List<SellOrderVO> sellOrderList(@RequestParam String membNo){
		List<SellOrderVO> list = membService.sellOrderList(membNo);
		//System.out.println(list);
		return list;
	}
	
	//매수 주문 현황
	@ResponseBody
	@PostMapping("buyOrderList")
	public List<BuyOrderVO> buyOrderList(@RequestParam String membNo){
		List<BuyOrderVO> list = membService.buyOrderList(membNo);
		return list;
	}
	
	//매도 주문 삭제
	@ResponseBody
	@PostMapping("deleteSellOrder")
	public int deleteSellOrder(@RequestParam String membNo, @RequestParam String sellNo, SellOrderVO soVO) {
		soVO.setMembNo(membNo);
		soVO.setSellNo(sellNo);
		int result = membService.deleteSellOrder(soVO);
		return result;
	}
	//매수 주문 취소
	@ResponseBody
	@PostMapping("deleteBuyOrder")
	public int deleteBuyOrder(@RequestParam String membNo, @RequestParam String buyNo, BuyOrderVO boVO) {
		boVO.setMembNo(membNo);
		boVO.setBuyNo(buyNo);
		int result = membService.deleteBuyOrder(boVO);
		return result;
	}
	
	//관심종목 삭제
	@ResponseBody
	@PostMapping("deleteInterest")
	public int deleteInterest(@RequestParam String membNo ,@RequestParam  String itemNo) {
		int result = membService.deleteInterest(membNo, itemNo);
		return result;
	}
	
	@ResponseBody
	@PostMapping("updateSellOrder")
	public int updateSellOrder(@RequestParam String membNo, @RequestParam String sellNo,@RequestParam int prc ,@RequestParam int rmnCnt, SellOrderVO soVO) {
		soVO.setMembNo(membNo);
		soVO.setSellNo(sellNo);
		soVO.setPrc(prc);
		soVO.setRmnCnt(rmnCnt);
		int result = membService.updateSellOrder(soVO);
		return result;
	}
	@ResponseBody
	@PostMapping("updateBuyOrder")
	public int updateBuyOrder(@RequestParam String membNo, @RequestParam String buyNo,@RequestParam int prc ,@RequestParam int rmnCnt, BuyOrderVO boVO) {
		boVO.setMembNo(membNo);
		boVO.setBuyNo(buyNo);
		boVO.setPrc(prc);
		boVO.setRmnCnt(rmnCnt);
		int result = membService.updateBuyOrder(boVO);
		return result;
	}
	
	@ResponseBody
	@PostMapping("dealList")
	public List<DealVO> dealList(DealVO vo){
		System.out.println("~~~~~~~~~~~~~~~~~~");
		System.out.println(membService.dealList(vo));
		return membService.dealList(vo);
	}
	
	//거래내역 리스트 개수
	@ResponseBody
	@PostMapping("getDealCount")
	public int getDealCount(DealVO vo) {
		return membService.getDealCount(vo);
	}
	
	@ResponseBody
	@PostMapping("myPossStockList")
	public List<PossVO> myPossStockList(@RequestParam String membNo){
		List<PossVO> list = membService.myPossStockList(membNo);
		return list;
	}
	
	@ResponseBody
	@PostMapping("buysellList")
	public List<DealVO> buysellList(DealVO vo){
		List<DealVO> list = membService.buysellList(vo);
		System.out.println("매도/매수 거래내역"+list);
		return list;
	}
	
	@ResponseBody
	@PostMapping("insertsurvey")
	public int insertsurvey(@RequestParam String invstTypeNo, @RequestParam String membNo, SurveyVO vo) {
		vo.setMembNo(membNo);
		vo.setInvstTypeNo(invstTypeNo);
		System.out.println("설문조사 : "+vo);
		int result = membService.insertsurvey(vo);
		System.out.println(result);
		return result;
	}
	
	@ResponseBody
	@PostMapping("analysisResult")
	public List<SurveyVO> analysisResult(String membNo){
		List<SurveyVO> list = membService.analysisResult(membNo);
		return list;
	}
	
	@ResponseBody
	@PostMapping("myBuyRaiseList")
	public List<PossVO> myBuyRaiseList(String membNo){
		List<PossVO> list = membService.myBuyRaiseList(membNo);
		return list;
	}
	
	@ResponseBody
	@PostMapping("mySellRaiseList")
	public List<PossVO> mySellRaiseList(String membNo){
		List<PossVO> list = membService.mySellRaiseList(membNo);
		return list;
	}
	
}
