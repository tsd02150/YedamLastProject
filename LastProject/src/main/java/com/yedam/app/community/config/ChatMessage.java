package com.yedam.app.community.config;

import java.util.Date;

import lombok.Data;

@Data
public class ChatMessage {
	public String anonnick;
	public String drwupdt;
	public String message;
	
	public ChatMessage() {
		
	}
	
	public ChatMessage(String anonnick,String drwupdt,String message) {
		this.anonnick=anonnick;
		this.drwupdt=drwupdt;
		this.message=message;
	}
}
