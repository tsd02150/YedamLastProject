package com.yedam.app.community.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.community.mapper.ChatMapper;
import com.yedam.app.community.service.ChatParticipationVO;
import com.yedam.app.community.service.ChatRoomVO;
import com.yedam.app.community.service.ChatService;
import com.yedam.app.community.service.ChatVO;

@Service
public class ChatServiceImpl implements ChatService {

	@Autowired
	ChatMapper chatMapper;
	
	@Override
	public ChatRoomVO roomInfo(String roomNo) {
		return chatMapper.roomInfo(roomNo);
	}

	@Override
	public boolean sameNick(ChatParticipationVO vo) {
		return chatMapper.sameNick(vo)!=null;
	}

	@Override
	public boolean participation(ChatParticipationVO vo) {
		return chatMapper.participation(vo)>0;
	}

	@Override
	public ChatParticipationVO getParticipationInfo(String membNo) {
		return chatMapper.getParticipationInfo(membNo);
	}

	@Override
	public boolean insertChat(ChatVO vo) {
		return chatMapper.insertChat(vo)>0;
	}

	@Override
	public ChatVO getChat(String chatNo) {
		return chatMapper.getChat(chatNo);
	}

}
