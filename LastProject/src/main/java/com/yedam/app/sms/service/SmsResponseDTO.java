package com.yedam.app.sms.service;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SmsResponseDTO {
	String requestId;
	LocalDateTime requestTime;
	String statusCode;
	String statusName;
}