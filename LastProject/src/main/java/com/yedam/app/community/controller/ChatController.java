package com.yedam.app.community.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.community.config.ChatMessage;
import com.yedam.app.community.service.ChatParticipationVO;
import com.yedam.app.community.service.ChatService;
import com.yedam.app.community.service.ChatVO;
import com.yedam.app.security.service.UserVO;


@Controller
@RequestMapping("community")
public class ChatController {
	@Autowired
	ChatService chatService;
	
	@Autowired
	public SimpMessagingTemplate template;
	
	// K:방번호, V:방에 참여한 사람들 멤버번호
	public static Map<String, List<String>> particiList = new HashMap<String, List<String>>();
	
	
	
	
	@MessageMapping("/room/{roomno}")
	public void chatMessage(ChatMessage message,@DestinationVariable String roomno) throws Exception {

		template.convertAndSend("/topic/sendto/"+roomno,message);
	}
	
	@GetMapping("chat")
	public String chat(Model model, HttpSession session) {
		String membNo=((UserVO)session.getAttribute("loggedInMember")).getMembNo();
		ChatParticipationVO vo = new ChatParticipationVO();
		
		chatService.participation(vo, session);
		model.addAttribute("particiInfo",chatService.getParticipationInfo(membNo));
		model.addAttribute("particiList",chatService.selectParticiList("room-1"));
		chatService.addRoomCnt("room-1");
		model.addAttribute("roomInfo",chatService.roomInfo("room-1"));
		model.addAttribute("roomList",chatService.selectRoomList());
		System.out.println(chatService.selectParticiList("room-1"));
		System.out.println(chatService.selectRoomList());
		
		return "community/chat";
	}
	
	@PostMapping("addChat")
	@ResponseBody
	public ChatVO insertChat(ChatVO vo) {
		chatService.insertChat(vo);
		vo=chatService.getChat(vo.getChatNo());
		System.out.println(vo);
		return vo;
	}
}
