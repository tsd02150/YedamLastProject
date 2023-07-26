package com.yedam.app.community.service;

import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ChatVO {
	private String chatNo;
	private String cntn;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Date drwupDt;
	private String roomNo;
	private String anonNick;
	private String membNo;
	private String nick;
}
