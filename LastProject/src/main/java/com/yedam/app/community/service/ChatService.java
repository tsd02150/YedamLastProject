package com.yedam.app.community.service;

import java.util.List;

import javax.servlet.http.HttpSession;

public interface ChatService {
	// 방정보
	public ChatRoomVO roomInfo(String roomNo);
	// 같은 닉네임 여부확인
	public boolean sameNick(ChatParticipationVO vo);
	// 방에 닉네임 생성
	public boolean participation(ChatParticipationVO vo);
	// 접속정보
	public ChatParticipationVO getParticipationInfo(String membNo);
	// 채팅 추가
	public boolean insertChat(ChatVO vo);
	// 채팅 정보
	public ChatVO getChat(String chatNo);
	// 방에 인원 추가
	public boolean addRoomCnt(String roomNo);
	// 방에 인원 감소
	public boolean subtractRoomCnt(String membNo);
	// 방 전체 정보
	public List<ChatRoomVO> selectRoomList();
	// 방 참여자 전체 정보
	public List<ChatParticipationVO> selectParticiList(String roomNo);
	// 방 참여자 제거
	public boolean deletePartici(String membNo);
	// 방 생성
	public boolean insertChatRoom(ChatRoomVO vo);
	// 방 비교
	public boolean sameRoom(String nm);
	// 방에 인원이 없으면 방 삭제
	public boolean deleteRoom(String roomNo);
}
