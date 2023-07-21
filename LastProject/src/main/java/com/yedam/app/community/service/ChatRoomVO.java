package com.yedam.app.community.service;

import java.util.List;

import lombok.Data;

@Data
public class ChatRoomVO {
	private String roomNo;
	private String nm;
	private int conn;
	private int lmt;
	
	private String anonNick;
	private List<ChatParticipationVO> particiList;
}
