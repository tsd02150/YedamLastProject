package com.yedam.app.websocket.config;


import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.stereotype.Component;

import com.yedam.app.community.controller.ChatController;
import com.yedam.app.community.service.ChatParticipationVO;
import com.yedam.app.community.service.ChatService;
import com.yedam.app.security.service.UserVO;

@Component
public class StompHandler extends ChannelInterceptorAdapter{
	@Autowired
	ChatService chatService;
	
    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String sessionId = accessor.getSessionId();
        String membNo="";
        
        
        switch (accessor.getCommand()) {
            case CONNECT:
                // 유저가 Websocket으로 connect()를 한 뒤 호출됨
                break;
            case DISCONNECT:
                // 유저가 Websocket으로 disconnect() 를 한 뒤 호출됨 or 세션이 끊어졌을 때 발생함(페이지 이동~ 브라우저 닫기 등)
            	if(ChatController.mySession!=null) {
                	membNo=((UserVO)ChatController.mySession.getAttribute("loggedInMember")).getMembNo();
                	ChatController.mySession=null;
                	if(chatService.subtractRoomCnt(membNo)&&chatService.deletePartici(membNo)) {
                		System.out.println("deletePartici");
                	}
                }
                break;
            default:
                break;
        }

    }
}
