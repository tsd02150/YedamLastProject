package com.yedam.app.websocket.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.stereotype.Component;

import com.yedam.app.community.service.ChatParticipationVO;
import com.yedam.app.community.service.ChatService;
import com.yedam.app.member.service.MemberService;


@Component
public class StompHandler extends ChannelInterceptorAdapter{
	@Autowired
	ChatService chatService;
	
	@Autowired
	MemberService memberService;
	
    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        String targetMembNo="";
        
        switch (accessor.getCommand()) {
            case CONNECT:
                // 유저가 Websocket으로 connect()를 한 뒤 호출됨
                break;
            case DISCONNECT:
                // 유저가 Websocket으로 disconnect() 를 한 뒤 호출됨 or 세션이 끊어졌을 때 발생함(페이지 이동~ 브라우저 닫기 등)
            	targetMembNo=memberService.selectOneMemb(accessor.getUser().getName()).getMembNo();
            	ChatParticipationVO particiInfo = chatService.getParticipationInfo(targetMembNo);
            	
            	if(particiInfo!=null) {
            		chatService.subtractRoomCnt(targetMembNo);
            		chatService.deletePartici(targetMembNo);
            	}

            	targetMembNo="";
                break;
            default:
                break;
        }

    }
}
