package com.yedam.app.member.service;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class RegisterMail implements MailService {

	@Autowired
	JavaMailSender emailsender; // Bean 등록해둔 MailConfig 를 emailsender 라는 이름으로 autowired

	private String ePw; // 인증번호
    private String tempPW; //임시비밀번호
    
	// 메일 내용 작성
    @Override
    public MimeMessage createMessage(String to) throws MessagingException, UnsupportedEncodingException {
        // TODO Auto-generated method stub

        MimeMessage message = emailsender.createMimeMessage();

        message.addRecipients(RecipientType.TO, to);// 보내는 대상
        message.setSubject("어제팔걸 계정 임시 패스워드");// 제목

        String msgg = "";
        msgg += "<div style='margin:100px;'>";
        msgg += "<h1> 안녕하세요</h1>";
        msgg += "<h1> 주식모의사이트 어제팔걸입니다</h1>";
        msgg += "<br>";
        msgg += "<p>회원님의 임시 비밀번호 입니다<p>";
        msgg += "<p>해당 비밀번호로 로그인 후 패스워드 변경 부탁드립니다.<p>";
        msgg += "<br>";
        msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg += "<h3 style='color:blue;'>임시 비밀번호</h3>";
        msgg += "<div style='font-size:130%'>";
        msgg += "CODE : <strong>";
        msgg += tempPW + "</strong><div><br/> ";
        msgg += "</div>";
        message.setText(msgg, "utf-8", "html");// 내용
        message.setFrom(new InternetAddress("ihyang00@naver.com", "어제팔걸"));// 보내는 사람

        return message;
    }

    // 랜덤 값 생성
    @Override
    public String createKey() {
        Random rnd = new Random();
        StringBuilder buf = new StringBuilder();

        // 영어 대문자 1개 추가
        buf.append((char) (rnd.nextInt(26) + 65));

        // 특수문자 1개 추가
        buf.append(generateRandomSpecialCharacter(rnd));

        // 나머지 6개의 문자 추가
        for (int i = 0; i < 6; i++) {
            if (rnd.nextBoolean()) {
                // 랜덤 영어 소문자
                buf.append((char) (rnd.nextInt(26) + 97));
            } else {
                // 랜덤 숫자
                buf.append(rnd.nextInt(10));
            }
        }
        return buf.toString();
    }

    // 특수문자를 랜덤하게 생성하는 메소드
    private char generateRandomSpecialCharacter(Random rnd) {
        char[] specialCharacters = { '!', '@', '#', '$', '%', '^', '&', '*' };

        int randomIndex = rnd.nextInt(specialCharacters.length);
        return specialCharacters[randomIndex];
    }

    // 메일 발송
    @Override
    public String sendTempPwdMessage(String to) throws Exception {
        tempPW = createKey();

        MimeMessage message = createMessage(to);
        try {// 예외처리
            emailsender.send(message);
        } catch (MailException es) {
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        System.out.println("임시비밀번호 : "+tempPW);
        return tempPW;
    }
///////////////////////////////////////
	

	// 메일 내용 작성
	@Override
	public MimeMessage createMessageNum(String to) throws MessagingException, UnsupportedEncodingException {
//		System.out.println("보내는 대상 : " + to);
//		System.out.println("인증 번호 : " + ePw);
		
		MimeMessage message = emailsender.createMimeMessage();

		message.addRecipients(RecipientType.TO, to);// 보내는 대상
		message.setSubject("어제팔걸 인증번호입니다.");// 제목

		String msgg = "";
		msgg += "<div style='margin:100px;'>";
		msgg += "<h1> 안녕하세요</h1>";
		msgg += "<h1> 주식모의사이트 어제팔걸입니다</h1>";
		msgg += "<br>";
		msgg += "<p>아래 코드를 사이트로 돌아가 입력해주세요<p>";
		msgg += "<br>";
		msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
		msgg += "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
		msgg += "<div style='font-size:130%'>";
		msgg += "CODE : <strong>";
		msgg += ePw + "</strong><div><br/> "; // 메일에 인증번호 넣기
		msgg += "</div>";
		message.setText(msgg, "utf-8", "html");// 내용, charset 타입, subtype
		// 보내는 사람의 이메일 주소, 보내는 사람 이름
		message.setFrom(new InternetAddress("ihyang00@naver.com", "어제팔걸"));// 보내는 사람

		return message;
	}

	// 랜덤 인증 코드 전송
	@Override
	public String createKeyNum() {
		StringBuffer key = new StringBuffer();
		Random rnd = new Random();

		for (int i = 0; i < 6; i++) { // 인증코드 6자리
	        key.append(rnd.nextInt(10)); // 0~9 사이의 랜덤 숫자 추가
	    }
		return key.toString();
	}

	// 메일 발송
	@Override
	public String sendSimpleMessage(String to) throws Exception {

		ePw = createKeyNum(); // 랜덤 인증번호 생성

		MimeMessage message = createMessageNum(to); // 메일 발송
		try {// 예외처리
			emailsender.send(message);
		} catch (MailException es) {
			es.printStackTrace();
			throw new IllegalArgumentException();
		}


		return ePw; // 메일로 보냈던 인증 코드를 서버로 반환
	}
}