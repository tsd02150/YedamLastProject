package com.yedam.app.community.service;

import java.util.List;

import javax.servlet.http.HttpSession;

public interface ChatService {
	// 방정보
	public ChatRoomVO roomInfo(String roomNo);
	// 같은 닉네임 여부확인
	public boolean sameNick(ChatParticipationVO vo);
	// 방에 닉네임 생성
	public boolean participation(ChatParticipationVO vo,HttpSession session);
	// 접속정보
	public ChatParticipationVO getParticipationInfo(String membNo);
	// 채팅 추가
	public boolean insertChat(ChatVO vo);
	// 채팅 정보
	public ChatVO getChat(String chatNo);
	// 방에 인원 추가
	public boolean addRoomCnt(String roomNo);
	// 방 전체 정보
	public List<ChatRoomVO> selectRoomList();
	// 방 참여자 전체 정보
	public List<ChatParticipationVO> selectParticiList(String roomNo);
}
