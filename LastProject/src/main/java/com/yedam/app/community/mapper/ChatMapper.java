package com.yedam.app.community.mapper;

import com.yedam.app.community.service.ChatParticipationVO;
import com.yedam.app.community.service.ChatRoomVO;
import com.yedam.app.community.service.ChatVO;

public interface ChatMapper {
	// 방정보
	public ChatRoomVO roomInfo(String roomNo);
	// 같은 닉네임 여부확인
	public ChatParticipationVO sameNick(ChatParticipationVO vo);
	// 방에 닉네임 생성
	public int participation(ChatParticipationVO vo);
	// 접속정보
	public ChatParticipationVO getParticipationInfo(String membNo);
	// 채팅 추가
	public int insertChat(ChatVO vo);
	// 채팅 정보
	public ChatVO getChat(String chatNo);
}
