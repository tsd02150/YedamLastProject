package com.yedam.app.community.controller;

import java.util.ArrayList;
import java.util.List;

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
	
	@MessageMapping("/room/{roomno}")
	public void chatMessage(ChatMessage message,@DestinationVariable String roomno) throws Exception {

		template.convertAndSend("/topic/sendto/"+roomno,message);
	}
	
	@GetMapping("chat")
	public String chat(Model model, HttpSession session) {
		model.addAttribute("roomInfo",chatService.roomInfo("room-1"));
		
		List<String> nickList1 = List.of("착한 ","나쁜 ","귀여운 ","즐거운 ","화난 ","배고픈 ","재밌는 ","궁금한 ","큰 ","멋진 ");
		List<String> nickList2 = List.of("사자","호랑이","강아지","고양이","고래","상어","소","말","개구리","돼지","양","쥐","토끼","뱀","닭","원숭이","치타");
		String anonNick;
		ChatParticipationVO vo = new ChatParticipationVO();
		String membNo=((UserVO)session.getAttribute("loggedInMember")).getMembNo();
		vo.setMembNo(membNo);
		vo.setRoomNo("room-1");
		do {
			int randomNum1 = (int)(Math.random() * nickList1.size());
			int randomNum2 = (int)(Math.random() * nickList2.size());
			anonNick=nickList1.get(randomNum1)+nickList2.get(randomNum2);
			System.out.println(anonNick);
			
			vo.setAnonNick(anonNick);
		}while(chatService.sameNick(vo));
		
		if(chatService.getParticipationInfo(membNo)==null)
			chatService.participation(vo);
		
		model.addAttribute("particiInfo",chatService.getParticipationInfo(membNo));
		
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
