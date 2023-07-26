package com.yedam.app.admin.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.admin.service.AdminService;
import com.yedam.app.admin.service.MembManageVO;
import com.yedam.app.community.service.NoticeVO;
import com.yedam.app.community.service.ReportVO;
//김태연 2023/07/24 admin 페이지
@Controller
@RequestMapping("admin")
public class AdminController {
	
	@Autowired
	AdminService adminService;
	
	// 회원관리 페이지 이동
	@GetMapping("memberManage")
	public String memberManage() {
		return "admin/adminMember";
	}
	// 신고관리 페이지 이동
	@GetMapping("reportManage")
	public String reportManage() {
		return "admin/adminReport";
	}
	// 공지사항 페이지 이동
	@GetMapping("adminNotice")
	public String adminNotice() {
		return "admin/adminNotice";
	}
	//주문처리관리 페이지 이동
	@GetMapping("adminOrder")
	public String adminOrder() {
		return "admin/adminOrder";
	}
	//제품관리 페이지 이동
	@GetMapping("adminProduct")
	public String adminProduct() {
		return "admin/adminProduct";
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
	public NoticeVO noticeDetail(String notiNo) {
		return adminService.noticeDetail(notiNo);
	}
}
