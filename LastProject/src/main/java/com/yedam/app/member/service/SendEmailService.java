package com.yedam.app.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmailService {
	@Autowired
	private JavaMailSender mailSender; // 메일 발신자
	
	public void sendEmail(String toEmail, String subject, String body) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("ihyang00@gmail.com");
		message.setTo(toEmail);
		message.setText(body);
		message.setSubject(subject);
		
//		mailSender.send(message);  ?? 빌드패스???? 라이브러리?찾아보기
		
		System.out.println("Mail Send Success");
	}
	
	
}
