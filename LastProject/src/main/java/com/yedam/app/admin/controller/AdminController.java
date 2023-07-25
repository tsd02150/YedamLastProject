package com.yedam.app.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
//김태연 2023/07/24 admin 페이지
@Controller
@RequestMapping("admin")
public class AdminController {
	
	@Autowired
	AdminService adminService;
	
	@GetMapping("memberManage")
	public String memberManage() {
		return "admin/adminMember";
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
	
	//회원정지
	@ResponseBody
	@GetMapping("memberBan")
	public int memberBan(@RequestParam List<String> list	, Integer period) {
		return adminService.memberBan(list, period);
	}
	
	//회원삭제
	@ResponseBody
	@PostMapping("deleteMemb")
	public int deleteMemb(@RequestBody List<String> rowKeys) {
		int result = adminService.deleteMember(rowKeys);
		return result;
	}
	
	//회원 정지해제
	@ResponseBody
	@GetMapping("returnMemb")
	public int returnMemb(@RequestParam List<String> list) {
		int result = adminService.returnNorm(list);
		return result;
	}
}
