package com.yedam.app.sms.service;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SmsRequestDTO {
	String type;
	String contentType;
	String countryCode;
	String from;
	String content;
	List<MessageDTO> messages;
	
}