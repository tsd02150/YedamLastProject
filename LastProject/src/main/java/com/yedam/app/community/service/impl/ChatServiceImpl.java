package com.yedam.app.community.service.impl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.community.mapper.ChatMapper;
import com.yedam.app.community.service.ChatParticipationVO;
import com.yedam.app.community.service.ChatRoomVO;
import com.yedam.app.community.service.ChatService;
import com.yedam.app.community.service.ChatVO;
import com.yedam.app.security.service.UserVO;

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
		
		List<String> nickList1 = List.of("착한 ","나쁜 ","귀여운 ","즐거운 ","화난 ","배고픈 ","재밌는 ","궁금한 ","큰 ","멋진 ");
		List<String> nickList2 = List.of("사자","호랑이","강아지","고양이","고래","상어","소","말","개구리","돼지","양","쥐","토끼","뱀","닭","원숭이","치타");
		String anonNick;
				
		do {
			int randomNum1 = (int)(Math.random() * nickList1.size());
			int randomNum2 = (int)(Math.random() * nickList2.size());
			anonNick=nickList1.get(randomNum1)+nickList2.get(randomNum2);
			vo.setAnonNick(anonNick);
		}while(chatMapper.sameNick(vo)!=null);

		if(chatMapper.getParticipationInfo(vo.getMembNo())==null) {
			chatMapper.addRoomCnt(vo.getRoomNo());
			return chatMapper.participation(vo)>0;			
		}else {
			return false;
		}
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

	@Override
	public boolean addRoomCnt(String roomNo) {
		return chatMapper.addRoomCnt(roomNo)>0;
	}
	
	@Override
	public boolean subtractRoomCnt(String membNo) {
		return chatMapper.subtractRoomCnt(membNo)>0;
	}
	@Override
	public List<ChatRoomVO> selectRoomList() {
		return chatMapper.selectRoomList();
	}

	@Override
	public List<ChatParticipationVO> selectParticiList(String roomNo) {
		return chatMapper.selectParticiList(roomNo);
	}

	@Override
	public boolean deletePartici(String membNo) {
		return chatMapper.deletePartici(membNo)>0;
	}

	@Override
	public boolean insertChatRoom(ChatRoomVO vo) {
		return chatMapper.insertChatRoom(vo)>0;
	}

	@Override
	public boolean sameRoom(String nm) {
		if(chatMapper.sameRoom(nm)!=null) {
			return false;
		}else {
			return true;			
		}
	}

	@Override
	public boolean deleteRoom(String roomNo) {
		return chatMapper.deleteRoom(roomNo)>0;
	}
	
	

}
