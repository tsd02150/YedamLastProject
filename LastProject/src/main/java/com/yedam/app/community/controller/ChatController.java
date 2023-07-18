package com.yedam.app.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yedam.app.community.service.ChatService;

@Controller
@RequestMapping("community")
public class ChatController {
	@Autowired
	ChatService chatService;
	
	@GetMapping("chat")
	public String chat() {
		
		
		return "community/chat";
	}
}
