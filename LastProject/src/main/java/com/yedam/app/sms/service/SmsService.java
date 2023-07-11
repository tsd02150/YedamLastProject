package com.yedam.app.sms.service;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.MediaType;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;

@PropertySource("classpath:application.properties")
@Slf4j
@RequiredArgsConstructor
@Service
public class SmsService {
	
	//header 암호화 - properties
	
	@Value("${naver-cloud-sms.accessKey}")
	private String accessKey;
	
	@Value("${naver-cloud-sms.accessKey}")
	private String secretKey;
	
	@Value("${naver-cloud-sms.accessKey}")
	private String serviceId;
	
	@Value("${naver-cloud-sms.accessKey}")
	private String phone;
	
	//전달 데이터 암호화
	public String makeSignature(Long time) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
		String space ="";
		String method = "POST";
		String newLine = "\n";
		String url = "/sms/v2/services/"+this.serviceId+"messages";
		String timestamp = time.toString();
        String accessKey = this.accessKey;
        String secretKey = this.secretKey;
        
        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(url)
                .append(newLine)
                .append(timestamp)
                .append(newLine)
                .append(accessKey)
                .toString();
        
        SecretKeySpec singningKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(singningKey);
        
        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
        String encodeBase64String = Base64.encodeBase64String(rawHmac);
        
		return encodeBase64String;
		
	}
	
	public SmsResponseDTO sendSms(MessageDTO messageDto) throws JsonProcessingException, RestClientException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
		Long time = System.currentTimeMillis();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("x-ncp-apigw-timestamp", time.toString());
		headers.set("x-ncp-iam-access-key", accessKey);
		headers.set("x-ncp-apigw-signature-v2", makeSignature(time));
		
		List<MessageDTO> messages = new ArrayList<>();
		messages.add(messageDto);
		
		SmsRequestDTO request = SmsRequestDTO.builder()
				.type("SMS")
				.contentType("COMM")
				.countryCode("82")
				.from(phone)
				.content(messageDto.getContent())
				.messages(messages)
				.build();
		
		ObjectMapper objectMapper = new ObjectMapper();
		String body = objectMapper.writeValueAsString(request);
		HttpEntity<String> httpBody = new HttpEntity<>(body,headers);
		
		RestTemplate restTemplate = new RestTemplate();
	    restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
	    SmsResponseDTO response = restTemplate.postForObject(new URI("https://sens.apigw.ntruss.com/sms/v2/services/"+ serviceId +"/messages"), httpBody, SmsResponseDTO.class);
 
	    return response;	
	}

}
