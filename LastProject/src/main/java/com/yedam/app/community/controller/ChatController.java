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
import com.yedam.app.community.service.ChatRoomVO;
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
		System.out.println(roomno);
		System.out.println(message);
		template.convertAndSend("/topic/sendto/"+roomno,message);
	}
	
	@GetMapping("chat")
	public String chat(Model model, HttpSession session) {
		String membNo=((UserVO)session.getAttribute("loggedInMember")).getMembNo();
		ChatParticipationVO vo = new ChatParticipationVO();	
		
		vo.setMembNo(membNo);
		vo.setRoomNo("room-1");
		chatService.participation(vo);
		vo=chatService.getParticipationInfo(membNo);

		model.addAttribute("particiInfo",vo);
		model.addAttribute("particiList",chatService.selectParticiList("room-1"));
		
		model.addAttribute("roomInfo",chatService.roomInfo("room-1"));
		model.addAttribute("roomList",chatService.selectRoomList());
		
		return "community/chat";
	}
	
	@PostMapping("addChat")
	@ResponseBody
	public ChatVO insertChat(ChatVO vo) {
		chatService.insertChat(vo);
		vo=chatService.getChat(vo.getChatNo());
		return vo;
	}
	
	@PostMapping("addChatRoom")
	@ResponseBody
	public ChatRoomVO insertChatRoom(ChatRoomVO vo) {
		if(chatService.sameRoom(vo.getNm())) {
			if(chatService.insertChatRoom(vo)) {
				vo=chatService.roomInfo(vo.getRoomNo());
			}
			return vo;
		}else {
			return null;
		}
	}
	
	@PostMapping("moveRoom")
	@ResponseBody
	public ChatRoomVO moveRoom(ChatRoomVO vo,HttpSession session) {
		System.out.println(vo);

		ChatParticipationVO particiInfo=new ChatParticipationVO();
		particiInfo.setRoomNo(vo.getRoomNo());
		String membNo=((UserVO)session.getAttribute("loggedInMember")).getMembNo();
		particiInfo.setMembNo(membNo);
		chatService.subtractRoomCnt(membNo);
		chatService.deletePartici(membNo);
		chatService.participation(particiInfo);

		vo=chatService.roomInfo(vo.getRoomNo());

		vo.setAnonNick(chatService.getParticipationInfo(membNo).getAnonNick());
		vo.setParticiList(chatService.selectParticiList(vo.getRoomNo()));
		System.out.println(vo);
		template.convertAndSend("/topic/partici/"+vo.getRoomNo(),vo);
		
		return vo;
	}
}
