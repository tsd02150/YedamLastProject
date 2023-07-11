package com.yedam.app.member.service;

import java.io.UnsupportedEncodingException;

import javax.mail.internet.MimeMessage;

import org.springframework.messaging.MessagingException;

public interface MailService{
	// 메일 내용 작성
		MimeMessage createMessage(String to) throws MessagingException, UnsupportedEncodingException, javax.mail.MessagingException;
		MimeMessage createMessageNum(String to) throws MessagingException, UnsupportedEncodingException, javax.mail.MessagingException;

		// 랜덤 인증 코드 전송
		String createKey();
		String createKeyNum();

		// 메일 발송
		String sendSimpleMessage(String to) throws Exception;

		String sendTempPwdMessage(String to) throws Exception;


}
