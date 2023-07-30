package com.yedam.app.common.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.common.service.AttachFileService;

@Controller
public class AttachFileController {
	@Autowired
	AttachFileService attachFileService;
	
	@PostMapping("insertAtch")
	@ResponseBody
	public String addAtch() {
		return null;
	}
	
	//공지사항 번호 딸린 파일삭제
	@ResponseBody
	@PostMapping("notiNoDelFile")
	public int notiNoDelFile(@RequestBody List<String> list) {
		return attachFileService.notiNoDelFile(list);
	}
}
