package com.yedam.app.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.yedam.app.community.service.ChatService;

@Controller
public class ChatController {
	@Autowired
	ChatService chatService;
}
