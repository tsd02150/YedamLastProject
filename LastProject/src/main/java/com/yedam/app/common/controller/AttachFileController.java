package com.yedam.app.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
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
}
