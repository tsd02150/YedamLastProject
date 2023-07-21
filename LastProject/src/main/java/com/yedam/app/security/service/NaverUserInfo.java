package com.yedam.app.security.service;

import java.util.Map;

public class NaverUserInfo implements OAuth2UserInfo {

	private Map<String, Object> attributes; //OAuth2User.getAttributes();
    private Map<String, Object> attributesResponse;

    public NaverUserInfo(Map<String, Object> attributes) {
        this.attributes = (Map<String, Object>) attributes.get("response");
        this.attributesResponse = (Map<String, Object>) attributes.get("response");
    }

	@Override
	public String getProviderId() {
		return attributesResponse.get("id").toString().substring(0, 22);
	}

	@Override
	public String getProvider() {
		return "Naver";
	}

	@Override
	public String getEmail() {
		return attributesResponse.get("email").toString();
	}

	@Override
	public String getName() {
		 return attributesResponse.get("name").toString();
	}

	@Override
	public String getNick() {
		return attributesResponse.get("nickname").toString();
	}

	@Override
	public String getTel() {
		return attributesResponse.get("mobile").toString();
	}

	@Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
    
    
}
