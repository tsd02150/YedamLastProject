package com.yedam.app.mall.controller;

import java.util.ArrayList;
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

import com.yedam.app.mall.service.BasketService;
import com.yedam.app.mall.service.BasketVO;
import com.yedam.app.mall.service.MallService;
import com.yedam.app.mall.service.OrderDetailVO;
import com.yedam.app.mall.service.OrderService;
import com.yedam.app.mall.service.OrderVO;
import com.yedam.app.mall.service.ShippingVO;
import com.yedam.app.member.service.AddrVO;
import com.yedam.app.member.service.MembVO;
import com.yedam.app.member.service.MemberService;
import com.yedam.app.security.service.UserVO;

@Controller
@RequestMapping("mall")
public class OrderController {

	@Autowired
	MallService mallService;

	@Autowired
	BasketService basketService;

	@Autowired
	OrderService orderService;

	@Autowired
	MemberService membService;

	@GetMapping("orderList")
	public String orderList(Model model, BasketVO bskVO, MembVO membVO, OrderVO ordVO, HttpSession session) {

		// UserVO mem = (UserVO) session.getAttribute("loggedInMember");
		String membNo = ((UserVO) session.getAttribute("loggedInMember")).getMembNo();
		UserVO mem = (UserVO) session.getAttribute("loggedInMember");
		model.addAttribute("member", membVO.getMembNo());
		model.addAttribute("membNo", membNo);
		model.addAttribute("mem", mem);
		// System.out.println("memInfo"+membNo);
		List<OrderVO> orderList = new ArrayList<OrderVO>();
		List<OrderVO> membInfo = new ArrayList<OrderVO>();
		List<String> list = orderService.membListInfo(mem.getMembNo());
		model.addAttribute("membList",list); //주소 포함된 멤버 리스트
		orderList = orderService.getOrderList(membNo);
		membInfo = orderService.getMemInfo(membNo);
		System.out.println("주문리스트 : " + orderList);
		model.addAttribute("orderList", orderList);
		model.addAttribute("membInfo", membInfo);
		System.out.println(membInfo);
		// model.addAttribute("orderList", orderService.getOrderList(ordVO));

		return "mall/orderList";

	}

	// 회원정보 수정
	@PostMapping("updateMemberInfo")
	public String updateMemberInfo(@RequestParam String id, @RequestParam String zip, @RequestParam String addr,
			@RequestParam String detaAddr, MembVO membVO, Model model, HttpSession session) {
		MembVO mem = membService.memberList(id);
		membVO.setId(id);


		// 주소 등록
		AddrVO addrInfo = new AddrVO();
		addrInfo.setZip(zip);
		addrInfo.setAddr(addr);
		addrInfo.setDetaAddr(detaAddr);
		addrInfo.setMembNo(mem.getMembNo());

		orderService.updateMemberInfo(membVO);
		
		orderService.insertAddr(addrInfo);

		// 수정한 정보 다시 세션에 저장
		MembVO list = orderService.memberList(id);
		UserVO loggedInMember = new UserVO();
		loggedInMember.setId(list.getId());
	
		loggedInMember.setPoint(list.getPoint());
		loggedInMember.setMembNo(list.getMembNo());
		loggedInMember.setJoinDt(list.getJoinDt());
		session.setAttribute("loggedInMember", loggedInMember);
		model.addAttribute("membList", list);

		return "redirect:orderList";
	}

	// 주소 수정
	@ResponseBody
	@PostMapping("updateMemberAddr")
	public AddrVO updateMemberAddr(@RequestParam String addrNo, @RequestParam String membNo, @RequestParam String zip,
			@RequestParam String addr, @RequestParam String detaAddr, AddrVO addrVO) {
		addrVO.setAddrNo(addrNo);
		addrVO.setZip(zip);
		addrVO.setAddr(addr);
		addrVO.setDetaAddr(detaAddr);
		addrVO.setMembNo(membNo);
		orderService.updateMemberAddr(addrVO);
		System.out.println(membService.updateMemberAddr(addrVO));
		return addrVO;
	}

	// 주소 추가
	@ResponseBody
	@PostMapping("insertMemberAddr")
	public AddrVO insertMemberAddr(@RequestParam String zip, @RequestParam String addr, @RequestParam String detaAddr,
			AddrVO addrVO) {
		addrVO.setZip(zip);
		addrVO.setAddr(addr);
		addrVO.setDetaAddr(detaAddr);
		orderService.insertAddr(addrVO);
		System.out.println(orderService.insertAddr(addrVO));
		return addrVO;
	}

	@ResponseBody
	@PostMapping("selectOneMembInfo")
	public List<MembVO> selectOneMembInfo(@RequestParam String id, MembVO membVO, Model model) {
		membVO.setId(id);
		model.addAttribute("info", orderService.selectOneMemb2(membVO));
		System.out.println("주소");
		System.out.println(membService.selectOneMemb2(membVO));
		return orderService.selectOneMemb2(membVO);
	}

	// 결제 성공
	@GetMapping("orderCheck")
	public String orderCheck(@RequestParam(value = "amount") int amount, HttpSession session, OrderVO ordVO,
			Model model, MembVO membVO, ShippingVO shipVO, OrderDetailVO oddVO) {

		UserVO mem = (UserVO) session.getAttribute("loggedInMember");
		// chargeVO.setMembNo(mem.getMembNo());
		// chargeVO.setChagPrc(amount);
		ordVO.setMembNo(mem.getMembNo());
		ordVO.setPayAn(amount);
		
		membVO.setId(mem.getId());

		// 결제 정보 저장, 등록
		// 포인트적립
		// membService.insertCharge(chargeVO, membVO);
		orderService.insertOrder(ordVO, membVO); // 수정된 정보 세션에 다시 저장.
		orderService.insertOrderDetail(oddVO, membVO);
		orderService.insertShipping(shipVO, membVO);
		
		mem.setPoint(membVO.getPoint());
		session.setAttribute("loggedInMember", mem);
		model.addAttribute("membList", membVO); // update된 회원 정보
		
		return "mall/orderCheck";
	}

	// 결제 취소 페이지
	@GetMapping("orderCancel")
	public String orderCancel(Model model, OrderVO ordVO, HttpSession session) {
		UserVO mem = (UserVO) session.getAttribute("loggedInMember");
		return "mall/orderCancel";
	}

}
