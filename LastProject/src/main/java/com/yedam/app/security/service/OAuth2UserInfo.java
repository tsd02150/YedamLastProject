package com.yedam.app.security.service;

import java.util.Map;

public interface OAuth2UserInfo {
	String getProviderId();
	String getProvider();
	String getEmail();
	String getName();
	String getNick();
	String getTel();
	
	Map<String, Object> getAttributes();

}
