package com.yedam.app.member.controller;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import com.yedam.app.mall.service.OrderService;
import com.yedam.app.mall.service.OrderVO;
import com.yedam.app.mall.service.ProductVO;
import com.yedam.app.mall.service.ShippingVO;
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
	
	@Autowired
	MemberService membService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	RegisterMail registerMail;

	@Autowired
	SmsService smsService;
	
	@Autowired
	private PasswordEncoder pwEncoder;

	//접근권한 메세지 session 삭제
	@ResponseBody
	@PostMapping("removeNoAccess")
	public String removeNoAccess(HttpSession session) {
		session.removeAttribute("noAccess");
		return "success";
	}
	
	//loginForm url referer 호출
	@GetMapping("returnUrl")
	@ResponseBody
	public String returnUrl(HttpServletRequest request,HttpSession session) {
		//loginForm에서 이전 페이지로 이동할 경우 main.html에서 returnUrl 호출
		if(!((String)request.getHeader("referer")).equals("http://43.202.20.221:83/member/login")) {
			session.setAttribute("returnUrl", request.getHeader("referer"));
			String temp = (String) session.getAttribute("returnUrl");
			return temp;			
		}
		return "fail";
	}
	
	//회원가입 Form
	@GetMapping("join")
	public String joinForm() {
		return "member/joinMember";
	}
	
	//회원가입 - member insert
	@PostMapping("join")
	public String joinMemb(MembVO membVO, AddrVO addrVO, Model model) {
        
		//회원가입 처리
		membVO.setPwd(pwEncoder.encode(membVO.getPwd()));//비밀번호 암호화
		if(membVO.getPath()==null) {
			membVO.setPath(null);
		}
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
		 
	// 이메일 인증코드 발송
	@PostMapping("mailConfirm")
	@ResponseBody
	String mailConfirm(@RequestParam("id") String id) throws Exception {
		String email = membService.selectOneMemb(id).getEmail();
		String code = registerMail.sendSimpleMessage(email);
		return code;
	}
	
	// sms 인증번호
	@PostMapping("smsConfirm")
	@ResponseBody
	String smsConfirm(@RequestParam("email") String email) throws Exception {
		String code = registerMail.sendSimpleMessage(email);
		return code;
	}

	// 임시비밀번호 발급 - 이메일
	@ResponseBody
	@PostMapping("mailConfirmPwd")
	public String findPwdEmail(@RequestParam("id") String id, MembVO membVO) throws Exception {
		String email = membService.selectOneMemb(id).getEmail();
		String code = registerMail.sendTempPwdMessage(email); 
		
		//기존 비밀번호 임시 비밀번호로 수정.
		membVO.setPwd(pwEncoder.encode(code));
		membVO.setId(id);
		membVO.setTempPwd(pwEncoder.encode(code));
		membService.updatePwd(membVO);
		
		return code;
	}
	
	//sms 인증번호 발송
	@ResponseBody
	@PostMapping("sendSms")
	public SmsResponseDTO findId(MessageDTO messageDto) throws JsonProcessingException, RestClientException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException, URISyntaxException {
	    SmsResponseDTO response = smsService.sendSms(messageDto);
	    return response;
	}
	
	//임시 비밀번호 발급 후 페이지 호출
	@PostMapping("tempPwdSuccess")
	public String renewPwd(@RequestParam("id") String id, Model model) {
		String email = membService.selectOneMemb(id).getEmail();
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
		
		if(pwEncoder.matches(membVO.getTempPwd(),loggedInMember.getPwd())) { //암호화된 비밀번호 맞는지 확인
			membVO.setPwd(pwEncoder.encode(membVO.getPwd()));//비밀번호 암호화
			int result = membService.updateTempPwd(membVO);
			if(result == 1) {
				MembVO list = membService.memberList(membVO.getId());
			    UserVO updateMemb = new UserVO();
			    updateMemb.setId(list.getId());
			    updateMemb.setNick(list.getNick());
			    updateMemb.setEmail(list.getEmail());
			    updateMemb.setTel(list.getTel());
			    updateMemb.setPwd(list.getPwd());
			    updateMemb.setTempPwd(list.getTempPwd());
			    updateMemb.setPoint(list.getPoint());
			    updateMemb.setMembNo(list.getMembNo());
			    updateMemb.setJoinDt(list.getJoinDt());
			    updateMemb.setNm(list.getNm());
			    session.setAttribute("loggedInMember", updateMemb);
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
		return membService.myItemCheck();
	}
	
	//관심종목 리스트 선택 페이지
	@GetMapping("myItemCheckForm")
	public String interestItemPage(Model model, InterestVO commonVO) {
		return "member/myItemCheckForm";
	}
	
	// 회원가입 완료 후 관심종목 선택 insert
	@ResponseBody
	@PostMapping("insertInterestItem")
	public int insertInterestItem(@RequestParam String membNo, @RequestParam String itemNo, MembVO membVO){
		membVO.setMembNo(membNo);
		membVO.setItemNo(itemNo);
		int test =membService.insertInterestItem(membVO);
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
	// id 찾기 성공 페이지 호출
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
	
	//회원관리 페이지 호출
	@GetMapping("mypage")
	public String myPageForm() {
		return "member/mypage";
	}
	
	//포인트|쿠폰 페이지 호출
	@GetMapping("mypoint")
	public String myPointForm(HttpSession session,MembVO membVO, Model model) {
		UserVO mem = (UserVO) session.getAttribute("loggedInMember");
		MembVO member = membService.memberList(mem.getId());
		
		//쿠폰 리스트
		List<CouponVO> coupon = membService.mycoupon(mem.getMembNo());
		
		model.addAttribute("mypoint",member.getPoint());
		model.addAttribute("mycoupon",coupon);
		model.addAttribute("startPage",1);
		return "member/mypoint";
	}
	
	//주식현황 페이지 호출
	@GetMapping("mystock")
	public String mystock(HttpSession session, Model model) {
		UserVO mem = (UserVO) session.getAttribute("loggedInMember");
		List<BuyOrderVO> buyOrderList = membService.buyOrderList(mem.getMembNo());
		List<SellOrderVO> sellOrderList = membService.sellOrderList(mem.getMembNo());
		model.addAttribute("sellOrderList",sellOrderList);
		model.addAttribute("buyOrderList",buyOrderList);
		return "member/mystock";
	}
	
	//설문조사
	@GetMapping("mysurvey")
	public String mysurvey() {
		return "member/mysurvey";
	}
	
	//주문내역
	@GetMapping("myorder")
	public String myorder(ShippingVO vo, Model model, HttpSession session) {

		 UserVO mem = (UserVO) session.getAttribute("loggedInMember"); Date date = new
		 Date(); SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd"); String
		 joinDt = format.format(mem.getJoinDt()); String endDt = format.format(date);
		 
		 vo.setMembNo(mem.getMembNo()); 
		 vo.setStartDate(joinDt); 
		 vo.setEndDate(endDt);
		 vo.setPage(1);
		 //배송중 List 
		 vo.setShipSt("배송중");
		 model.addAttribute("ing",membService.shipList(vo));
		 
		 //배송완료 List 
		 vo.setShipSt("배송완료");
		 model.addAttribute("complete",membService.shipList(vo));
		//취소 List 
		 vo.setShipSt(""); 
		 vo.setOrderSt("결제취소");
		 model.addAttribute("refund",membService.shipList(vo));

		return "member/myorder";
	}
	
	//포인트 충전 결제 성공 페이지
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
	
	//포인트 충전 실패 페이지
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
	
	//mypage - 내 관심종목 리스트 ajax 호출
	@ResponseBody
	@PostMapping("interestList")
	public List<StockVO> interestList(@RequestParam String membNo, StockVO stockVO) {
		List<StockVO> interestList = membService.interestList(membNo);
		return interestList;
	}
	
	//내 프로필 상세
	@GetMapping("mypageInfo")
	public String mypageInfo(HttpSession session, Model model, MembVO membVO) {
		UserVO mem = (UserVO) session.getAttribute("loggedInMember");
		String id = mem.getId();
		List<String> list = membService.membListInfo(mem.getMembNo());
		MembVO member = membService.memberList(id);
		model.addAttribute("membList",list); //주소 포함된 멤버 리스트
		model.addAttribute("member",member); //맴버 테이블만 조회
		model.addAttribute("stockList",membService.myPossStockList(mem.getMembNo()));
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
	                               @RequestParam String zip,@RequestParam String addr,@RequestParam String detaAddr,
	                               MembVO membVO, Model model, HttpSession session) {
		UserVO memb = (UserVO) session.getAttribute("loggedInMember");
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
	    
	    //주소 등록
	    AddrVO addrInfo = new AddrVO();
	    if(membService.membListInfo(memb.getMembNo())== null) {
	    	if(zip !=""  && addr != "" && detaAddr != "") { //list.get(i).toString().zip
	    		System.out.println("주소등록");
	    		addrInfo.setZip(zip);
	    		addrInfo.setAddr(addr);
	    		addrInfo.setDetaAddr(detaAddr);
	    		addrInfo.setMembNo(mem.getMembNo());
	    		
	    		membService.insertAddr(addrInfo);
	    	}
	    }
	    membService.updateMemberInfo(membVO);

	    //수정한 정보 다시 세션에 저장
	    MembVO list = membService.memberList(id);
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
	    loggedInMember.setNm(list.getNm());
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
		return addrVO;
	}
	
	//주소 추가
	@ResponseBody
	@PostMapping("insertMemberAddr")
	public AddrVO insertMemberAddr(AddrVO addrVO) {
		membService.insertAddr(addrVO);
		return addrVO;
	}
	
	@ResponseBody
	@PostMapping("selectOneMembInfo")
	public List<MembVO> selectOneMembInfo(@RequestParam String id, MembVO membVO,Model model) {
		membVO.setId(id);
		model.addAttribute("info", membService.selectOneMemb2(membVO));
		return membService.selectOneMemb2(membVO);
	}
	
	//내프로필->상세 페이지 클릭 시 비밀번호 비교
	@ResponseBody
	@PostMapping("mypageIntroCheckPwd")
	public String mypageIntroCheckPwd(@RequestParam String pwd, @RequestParam String id, MembVO membVO, Model model) {
		membVO = membService.memberList(id);
		if (pwEncoder.matches(pwd, membVO.getPwd())) {
			return "success";
		} else {
			return "fail";
		}
	}
	
	//주식현황 상세
	@GetMapping("mystockInfo")
	public String mystockInfo(HttpSession session, Model model) {
		UserVO mem = (UserVO) session.getAttribute("loggedInMember");
		
		List<PossVO> possstockList = membService.myStockRaise(mem.getMembNo());
		model.addAttribute("possstockList", possstockList);
		
		double sumNowPrc = possstockList.stream().mapToInt(PossVO::getNowPrc).sum(); //총 매수금액
		double sumTradePrc = possstockList.stream().mapToInt(PossVO::getTradePrc).sum(); //총 평가금액
	    double raise =(double) (sumNowPrc/sumTradePrc) * 100-100; //보유자산 총 수익률
	    System.out.println("수익률");
	    System.out.println(raise);
	    if(sumNowPrc == 0 || sumTradePrc==0 ) {
	    	raise = 0;
	    }
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
	public int deleteSellOrder(SellOrderVO soVO) {
		int result = membService.deleteSellOrder(soVO);
		return result;
	}
	//매수 주문 취소
	@ResponseBody
	@PostMapping("deleteBuyOrder")
	public int deleteBuyOrder(BuyOrderVO boVO, HttpSession session) {
		UserVO mem = (UserVO) session.getAttribute("loggedInMember");
		
		//매수 주문 삭제 시 포인트 반환
		MembVO membVO = new MembVO();
		int point = mem.getPoint()+boVO.getPoint();
		membVO.setPoint(point);
		membVO.setId(mem.getId());
		membService.updateMemberInfo(membVO);
		
		//session 정보 업데이트
		mem.setPoint(point);
		
		//주문 삭제
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
	
	//매도 주문 수정
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
	
	//매도주문 수정 후 보유주식 현황 수정
	@ResponseBody
	@PostMapping("updatePoss")
	public int updatePoss(PossVO vo, HttpSession session) {
		UserVO mem = (UserVO) session.getAttribute("loggedInMember");
		vo.setMembNo(mem.getMembNo());
		int result = membService.updatePoss(vo);
		return result;
	}
	
	//매수 주문 변경
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
	
	//거래 내역
	@ResponseBody
	@PostMapping("dealList")
	public List<DealVO> dealList(DealVO vo){
		return membService.dealList(vo);
	}
	
	//거래내역 리스트 개수
	@ResponseBody
	@PostMapping("getDealCount")
	public int getDealCount(DealVO vo) {
		return membService.getDealCount(vo);
	}
	
	//보유주식 내역
	@ResponseBody
	@PostMapping("myPossStockList")
	public List<PossVO> myPossStockList(@RequestParam String membNo){
		List<PossVO> list = membService.myPossStockList(membNo);
		return list;
	}
	
	//매도, 매수 내역
	@ResponseBody
	@PostMapping("buysellList")
	public List<DealVO> buysellList(DealVO vo){
		return membService.buysellList(vo);
	}
	
	//매도, 매수 내역 총 개수
	@ResponseBody
	@PostMapping("buysellCount")
	public int buysellCount(DealVO vo) {
		return membService.buysellCount(vo);
	}
	
	//설문조사 추가
	@ResponseBody
	@PostMapping("insertsurvey")
	public int insertsurvey(SurveyVO vo,HttpSession session) {
		UserVO mem = (UserVO) session.getAttribute("loggedInMember");
		vo.setMembNo(mem.getMembNo());
		int result = membService.insertsurvey(vo);
		return result;
	}
	
	//설문조사 결과 조회
	@ResponseBody
	@PostMapping("analysisResult")
	public List<SurveyVO> analysisResult(HttpSession session){
		UserVO mem = (UserVO) session.getAttribute("loggedInMember");
		List<SurveyVO> list = membService.analysisResult(mem.getMembNo());
		return list;
	}
	
	//차트  - 수익률 리스트
	@ResponseBody
	@PostMapping("myRaiseList")
	public List<PossVO> myRaiseList(String membNo){
		List<PossVO> list = membService.myRaiseList(membNo);
		return list;
	}
	
	//주식 추천 항목
	@ResponseBody
	@PostMapping("recomList")
	public List<PossVO> recomList(String membNo){
		List<PossVO> list = membService.recomList(membNo);
		return list;
	}
	
	//회원탈퇴
	@ResponseBody
	@PostMapping("deleteMemb")
	public int deleteMemb(String membNo, MembVO membVO, HttpSession session) {
		UserVO mem = (UserVO) session.getAttribute("loggedInMember");
		membVO = membService.selectOneMemb(mem.getId());
		membVO.setMembNo(membNo);
		membVO.setPoint(0);
		
		//회원 백업
		membService.insertbackup(membVO);
		//세션 삭제
		session.invalidate();
		return membService.deleteMemb(membNo);
	}
	
	//주문, 배송 내역
	@ResponseBody
	@PostMapping("shipList")
	public List<ShippingVO> shipList(ShippingVO vo, HttpSession session){
		UserVO mem = (UserVO) session.getAttribute("loggedInMember");
		vo.setMembNo(mem.getMembNo());
		return membService.shipList(vo);
	}
	
	//주문, 배송 내역 총 개수
	@ResponseBody
	@PostMapping("getShipListCount")
	public int getShipListCount(ShippingVO vo, HttpSession session){
		UserVO mem = (UserVO) session.getAttribute("loggedInMember");
		vo.setMembNo(mem.getMembNo());
		return membService.getShipListCount(vo);
	}
	
	//주문 상세 내역
	@ResponseBody
	@PostMapping("myorderDetaList")
	public List<ShippingVO> myorderDetaList(ShippingVO vo, HttpSession session){
		UserVO mem = (UserVO) session.getAttribute("loggedInMember");
		vo.setMembNo(mem.getMembNo());
		return membService.myorderDetaList(vo);
	}
	
	//주문 취소
	@ResponseBody
	@PostMapping("deleteOrder")
	public int deleteOrder(OrderVO vo, HttpSession session) {
		UserVO mem = (UserVO) session.getAttribute("loggedInMember");
		String membNo = mem.getMembNo();
		MembVO membVO = new MembVO();
		
		//주문금액
		int orderAm = Integer.parseInt(membService.selectOrderOne(vo));
		int point = mem.getPoint()-orderAm;
		
		//주문취소 변경
		vo.setMembNo(membNo);
		int result = membService.deleteOrder(vo);
		
		//회원정보 변경
		membVO.setId(mem.getId());
		membVO.setPoint(point);
		membService.updateMemberInfo(membVO);
		
		//session 정보 업데이트
		mem.setPoint(point);
		
		return result;
	}

	//주문 내역 수정
	@ResponseBody
	@PostMapping("updateShip")
	public int updateShip(ShippingVO vo, HttpSession session ) {
		UserVO mem = (UserVO) session.getAttribute("loggedInMember");
		int result = membService.updateShip(vo);
		return result;
	}
	
}
