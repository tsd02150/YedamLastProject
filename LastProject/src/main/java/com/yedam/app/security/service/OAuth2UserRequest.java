package com.yedam.app.security.service;

import java.util.Map;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class OAuth2UserRequest {
	private final ClientRegistration clientRegistration;//인증서버의 정보
	private final OAuth2AccessToken accessToken; //인증 토큰값
	private final Map<String, Object> additionalParameters;	 //유저의 정보
}