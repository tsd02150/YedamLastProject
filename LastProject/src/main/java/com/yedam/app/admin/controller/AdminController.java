package com.yedam.app.admin.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.admin.service.AdminService;
import com.yedam.app.admin.service.MembManageVO;
import com.yedam.app.community.service.BoardVO;
import com.yedam.app.community.service.ChatVO;
import com.yedam.app.community.service.FaqVO;
import com.yedam.app.community.service.NoticeVO;
import com.yedam.app.community.service.QuestionVO;
import com.yedam.app.community.service.ReportVO;
import com.yedam.app.mall.service.CommonCdVO;
import com.yedam.app.mall.service.OrderVO;
import com.yedam.app.mall.service.ProductVO;
import com.yedam.app.security.service.UserVO;
//김태연 2023/07/24 admin 페이지
@Controller
@RequestMapping("admin")
public class AdminController {
	
	@Autowired
	AdminService adminService;
	
	// 회원관리 페이지 이동
	@GetMapping("memberManage")
	public String memberManage(Integer sideNumber ,Model model) {
		sideNumber = sideNumber == null ? 1 : sideNumber;
		model.addAttribute("sideNumber",sideNumber);
		return "admin/adminMember";
	}
	// 신고관리 페이지 이동
	@GetMapping("reportManage")
	public String reportManage(Integer sideNumber ,Model model) {
		model.addAttribute("sideNumber",sideNumber);
		return "admin/adminReport";
	}
	// 공지사항 페이지 이동
	@GetMapping("adminNotice")
	public String adminNotice(Integer sideNumber ,Model model) {
		model.addAttribute("sideNumber",sideNumber);
		return "admin/adminNotice";
	}
	//주문처리관리 페이지 이동
	@GetMapping("adminOrder")
	public String adminOrder(Integer sideNumber ,Model model) {
		model.addAttribute("sideNumber",sideNumber);
		return "admin/adminOrder";
	}
	//제품관리 페이지 이동
	@GetMapping("adminProduct")
	public String adminProduct(Integer sideNumber ,Model model) {
		model.addAttribute("sideNumber",sideNumber);
		return "admin/adminProduct";
	}
	// qna/faq 페이지 이동
	@GetMapping("adminQNA")
	public String adminQNA(Integer sideNumber ,Model model) {
		model.addAttribute("sideNumber",sideNumber);
		return "admin/adminQNA";
	}
	// adminBoard 페이지 이동
	@GetMapping("adminBoard")
	public String adminBoard(Integer sideNumber ,Model model) {
		model.addAttribute("sideNumber",sideNumber);
		return "admin/adminBoard";
	}
	// adminChat 페이지로 이동
	@GetMapping("adminChat")
	public String adminChat(Integer sideNumber, Model model) {
		model.addAttribute("roomList",adminService.getRoomList());
		model.addAttribute("sideNumber",sideNumber);
		return "admin/adminChat";
	}
	// 회원리스트
	@SuppressWarnings("unchecked")
	@ResponseBody
	@GetMapping("memberList")
	public Map<String,Object> membList(int page, int perPage){
		Map<String,Object> objectMap = new HashMap<>();
		Map<String,Object> dataMap = new HashMap<>();
		Map<String,Object> paginationMap = new HashMap<>();
		Map<String,Object> resultMap = adminService.getMembList(page , perPage);
		objectMap.put("result", true);
        objectMap.put("data", dataMap);
	        dataMap.put("contents", (List<MembManageVO>)resultMap.get("membList"));
	        dataMap.put("pagination", paginationMap);
		        paginationMap.put("page", page);
		        paginationMap.put("totalCount", (Integer)resultMap.get("membTotal"));
        return objectMap;
	}
	
	// 신고글 리스트
	@SuppressWarnings("unchecked")
	@ResponseBody
	@GetMapping("reportList")
	public Map<String,Object> reportList(int page , int perPage){
		Map<String,Object> objectMap = new HashMap<>();
		Map<String,Object> dataMap = new HashMap<>();
		Map<String,Object> paginationMap = new HashMap<>();
		Map<String,Object> resultMap = adminService.reportList(page , perPage);
		objectMap.put("result", true);
        objectMap.put("data", dataMap);
	        dataMap.put("contents", (List<ReportVO>)resultMap.get("reportList"));
	        dataMap.put("pagination", paginationMap);
		        paginationMap.put("page", page);
		        paginationMap.put("totalCount", (Integer)resultMap.get("reportTotal"));
        return objectMap;
	}
	
	// 공지사항 리스트
	@SuppressWarnings("unchecked")
	@ResponseBody
	@GetMapping("noticeList")
	public Map<String,Object> noticeList(int page , int perPage){
		Map<String,Object> objectMap = new HashMap<>();
		Map<String,Object> dataMap = new HashMap<>();
		Map<String,Object> paginationMap = new HashMap<>();
		Map<String,Object> resultMap = adminService.noticeList(page , perPage);
		objectMap.put("result", true);
        objectMap.put("data", dataMap);
	        dataMap.put("contents", (List<NoticeVO>)resultMap.get("noticeList"));
	        dataMap.put("pagination", paginationMap);
		        paginationMap.put("page", page);
		        paginationMap.put("totalCount", (Integer)resultMap.get("noticeTotal"));
        return objectMap;
	}
	
	// qna 리스트
	@SuppressWarnings("unchecked")
	@ResponseBody
	@GetMapping("qnaList")
	public Map<String,Object> qnaList(int page , int perPage){
		Map<String,Object> objectMap = new HashMap<>();
		Map<String,Object> dataMap = new HashMap<>();
		Map<String,Object> paginationMap = new HashMap<>();
		Map<String,Object> resultMap = adminService.qnaList(page , perPage);
		objectMap.put("result", true);
        objectMap.put("data", dataMap);
	        dataMap.put("contents", (List<QuestionVO>)resultMap.get("qnaList"));
	        dataMap.put("pagination", paginationMap);
		        paginationMap.put("page", page);
		        paginationMap.put("totalCount", (Integer)resultMap.get("qnaTotal"));
        return objectMap;
	}
	
	// faq 리스트
	@SuppressWarnings("unchecked")
	@ResponseBody
	@GetMapping("faqList")
	public Map<String,Object> faqList(int page , int perPage){
		Map<String,Object> objectMap = new HashMap<>();
		Map<String,Object> dataMap = new HashMap<>();
		Map<String,Object> paginationMap = new HashMap<>();
		Map<String,Object> resultMap = adminService.faqList(page , perPage);
		objectMap.put("result", true);
        objectMap.put("data", dataMap);
	        dataMap.put("contents", (List<FaqVO>)resultMap.get("faqList"));
	        dataMap.put("pagination", paginationMap);
		        paginationMap.put("page", page);
		        paginationMap.put("totalCount", (Integer)resultMap.get("faqTotal"));
        return objectMap;
	}
	// board 리스트
	@SuppressWarnings("unchecked")
	@ResponseBody
	@GetMapping("boardList")
	public Map<String,Object> boardList(int page , int perPage){
		Map<String,Object> objectMap = new HashMap<>();
		Map<String,Object> dataMap = new HashMap<>();
		Map<String,Object> paginationMap = new HashMap<>();
		Map<String,Object> resultMap = adminService.boardList(page , perPage);
		objectMap.put("result", true);
        objectMap.put("data", dataMap);
	        dataMap.put("contents", (List<BoardVO>)resultMap.get("boardList"));
	        dataMap.put("pagination", paginationMap);
		        paginationMap.put("page", page);
		        paginationMap.put("totalCount", (Integer)resultMap.get("boardTotal"));
        return objectMap;
	}
	// chat 리스트
	@SuppressWarnings("unchecked")
	@ResponseBody
	@GetMapping("chatList")
	public Map<String,Object> chatList(int page , int perPage){
		Map<String,Object> objectMap = new HashMap<>();
		Map<String,Object> dataMap = new HashMap<>();
		Map<String,Object> paginationMap = new HashMap<>();
		Map<String,Object> resultMap = adminService.chatList(page , perPage);
		objectMap.put("result", true);
        objectMap.put("data", dataMap);
	        dataMap.put("contents", (List<ChatVO>)resultMap.get("chatList"));
	        dataMap.put("pagination", paginationMap);
		        paginationMap.put("page", page);
		        paginationMap.put("totalCount", (Integer)resultMap.get("chatTotal"));
        return objectMap;
	}
	
	// orders 리스트
	@SuppressWarnings("unchecked")
	@ResponseBody
	@GetMapping("orderList")
	public Map<String,Object> orderList(int page , int perPage){
		Map<String,Object> objectMap = new HashMap<>();
		Map<String,Object> dataMap = new HashMap<>();
		Map<String,Object> paginationMap = new HashMap<>();
		Map<String,Object> resultMap = adminService.orderList(page , perPage);
		objectMap.put("result", true);
        objectMap.put("data", dataMap);
	        dataMap.put("contents", (List<OrderVO>)resultMap.get("orderList"));
	        dataMap.put("pagination", paginationMap);
		        paginationMap.put("page", page);
		        paginationMap.put("totalCount", (Integer)resultMap.get("orderTotal"));
        return objectMap;
        
	}
	
	// 주문처리상태 변경
	@ResponseBody
	@PostMapping("orderStUpdate")
	public int orderStUpdate(@RequestBody OrderVO rowKeys) {
		System.out.println(rowKeys);
		return adminService.orderStUpdate(rowKeys);
	}
	
	// 주문내역 삭제
	@ResponseBody
	@PostMapping("deleteOrder")
	public int deleteOrder(@RequestBody List<String> rowKeys) {
		return adminService.deleteOrder(rowKeys);
	}
		
	
	//회원정지
	@ResponseBody
	@GetMapping("memberBan")
	public int memberBan(@RequestParam List<String> list	, Integer period , HttpServletRequest request) {
		
		int result = adminService.memberBan(list, period);
		if(result > 0) {
			Enumeration<String> attributes = request.getSession().getAttributeNames();
			while (attributes.hasMoreElements()) {
			    String attribute = (String) attributes.nextElement();
			    System.err.println(attribute+" : "+request.getSession().getAttribute(attribute));
			}

		}
		return result;
	}
	
	//회원삭제
	@ResponseBody
	@PostMapping("deleteMemb")
	public int deleteMemb(@RequestBody List<String> rowKeys) {
		return adminService.deleteMember(rowKeys);
	}
	
	//회원 정지해제
	@ResponseBody
	@GetMapping("returnMemb")
	public int returnMemb(@RequestParam List<String> list) {
		return adminService.returnNorm(list);
	}
	
	// 신고글 정보
	@ResponseBody
	@GetMapping("reportInfo")
	public Map<String ,Object> reportInfo(String accused , String rprtNo){
		return adminService.reportInfo(accused,rprtNo);
	}
	
	//신고글처리상태 변경
	@ResponseBody
	@GetMapping("rprtStChange")
	public int rprtStChange(String rprtNo) {
		System.out.println("cont "+rprtNo);
		return adminService.rprtStChange(rprtNo);
	}
	
	// 신고글 삭제
	@ResponseBody
	@PostMapping("deleteReport")
	public int deleteReport(@RequestBody List<String> list) {
		return adminService.deleteReport(list);
	}
	
	// 공지사항 단건조회
	@ResponseBody
	@GetMapping("noticeDetail")
	public Map<String , Object > noticeDetail(String notiNo) {
		return adminService.noticeDetail(notiNo);
	}
	
	//공지사항 추가
	@ResponseBody
	@PostMapping("addNotice")
	public Map<String , Object> addNotice(NoticeVO vo,HttpServletRequest request) {
		UserVO uvo = (UserVO) request.getSession().getAttribute("loggedInMember");
		vo.setMembNo(uvo.getMembNo());
		return adminService.addNotice(vo);
	}
	
	//공지사항 수정
	@ResponseBody
	@PostMapping("modifyNotice")
	public Map<String , Object> modifyNotice(NoticeVO vo,HttpServletRequest request) {
		UserVO uvo = (UserVO) request.getSession().getAttribute("loggedInMember");
		vo.setMembNo(uvo.getMembNo());
		return adminService.modifyNotice(vo);
	}
	
	// 신고글 삭제
	@ResponseBody
	@PostMapping("deleteNotice")
	public int deleteNotice(@RequestBody List<String> list) {
		return adminService.deleteNotice(list);
	}
	
	// faq 단건조회
	@ResponseBody
	@GetMapping("faqDetail")
	public FaqVO faqDetail(String faqNo) {
		return adminService.faqDetail(faqNo);
	}
	
	// qna 단건조회
	@ResponseBody
	@GetMapping("qnaDetail")
	public QuestionVO qnaDetail(String qstNo) {
		return adminService.qnaDetail(qstNo);
	}
	
	//faq 수정
	@ResponseBody
	@PostMapping("modifyFaq")
	public Map<String , Object> modifyFaq(FaqVO vo) {
		return adminService.modifyFaq(vo);
	}
	//qna 수정
	@ResponseBody
	@PostMapping("modifyQna")
	public Map<String , Object> modifyQna(QuestionVO vo) {
		return adminService.modifyQna(vo);
	}
	
	//faq 삭제
	@ResponseBody
	@PostMapping("deleteFaq")
	public int deleteFaq(@RequestBody List<String> list) {
		return adminService.deleteFaq(list);
	}
	//qna 삭제
	@ResponseBody
	@PostMapping("deleteQna")
	public int deleteQna(@RequestBody List<String> list) {
		return adminService.deleteQna(list);
	}
	
	//faq 작성
	@ResponseBody
	@PostMapping("addFaq")
	public int addFaq(FaqVO vo) {
		return adminService.addFaq(vo);
	}
	
	// board 조회
	@ResponseBody
	@GetMapping("boardDetail")
	public BoardVO boardDetail(String boardNo) {
		return adminService.boardDetail(boardNo);
	}
	
	// board 삭제
	@ResponseBody
	@PostMapping("deleteBoard")
	public int deleteBoard(@RequestBody List<String> list) {
		return adminService.deleteBoard(list);
	}

		
	// product 리스트
	@SuppressWarnings("unchecked")
	@ResponseBody
	@GetMapping("productList")
	public Map<String,Object> productList(int page , int perPage){
		Map<String,Object> objectMap = new HashMap<>();
		Map<String,Object> dataMap = new HashMap<>();
		Map<String,Object> paginationMap = new HashMap<>();
		Map<String,Object> resultMap = adminService.productList(page , perPage);
		objectMap.put("result", true);
        objectMap.put("data", dataMap);
	        dataMap.put("contents", (List<ProductVO>)resultMap.get("productList"));
	        dataMap.put("pagination", paginationMap);
		        paginationMap.put("page", page);
		        paginationMap.put("totalCount", (Integer)resultMap.get("productTotal"));
        return objectMap;
	}
	
	//상품 등록
	@ResponseBody
	@PostMapping("addProduct")
	public Map<String , Object> addProduct(ProductVO prdtVO ,HttpServletRequest request) {
		UserVO uvo = (UserVO) request.getSession().getAttribute("loggedInMember");
		adminService.addCommonCd(prdtVO);
		prdtVO.setMembNo(uvo.getMembNo());
		return adminService.addProduct(prdtVO);
	}

	
	// 상품 단건조회
	@ResponseBody
	@GetMapping("productDetail")
	public Map<String , Object > productDetail(String prdtNo) {
		return adminService.productDetail(prdtNo);
	}
	
	//상품 수정
	@ResponseBody
	@PostMapping("modifyProduct")
	public Map<String , Object> modifyProduct(ProductVO prdtVO, HttpServletRequest request) {
		UserVO uvo = (UserVO) request.getSession().getAttribute("loggedInMember");
		prdtVO.setMembNo(uvo.getMembNo());
		return adminService.modifyProduct(prdtVO);
	}
	
	// 상품 삭제
	@ResponseBody
	@PostMapping("deleteProduct")
	public int deleteProduct(@RequestBody List<String> list) {
		
		adminService.deleteCommonCd(list);
		return adminService.deleteProduct(list);
	}

	
	// 응답안한 신고글 개수
	@ResponseBody
	@GetMapping("nonChkRptCnt")
	public int nonChkRptCnt() {
		return adminService.nonChkRptCnt();
	}
}
