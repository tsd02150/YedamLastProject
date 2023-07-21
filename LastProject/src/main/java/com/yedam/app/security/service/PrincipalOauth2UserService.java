package com.yedam.app.security.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.yedam.app.member.mapper.MemberMapper;
import com.yedam.app.member.service.MembVO;
import com.yedam.app.security.mapper.UserMapper;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
	@Autowired
	UserMapper userMapper;
	@Autowired
	MemberMapper membMapper;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) {
		BCryptPasswordEncoder pwEncord = new BCryptPasswordEncoder();
		OAuth2User oauth2User = super.loadUser(userRequest);
		
		OAuth2UserInfo oauth2UserInfo = null;
		String provider = userRequest.getClientRegistration().getRegistrationId();
		System.out.println(provider);
		
		if(provider.equals("kakao")) {
			oauth2UserInfo = new KakaoUserInfo(oauth2User.getAttributes());
		}else if(provider.equals("naver")) {
			oauth2UserInfo = new NaverUserInfo(oauth2User.getAttributes());
		}
		
		String providerId = oauth2UserInfo.getProviderId();
		
		//String uuid = UUID.randomUUID().toString().substring(0, 6);
		String username = provider; //+ "_" + uuid;
		String pwd = pwEncord.encode("1234");
		if(oauth2UserInfo.getNick()!=null) {
			username = oauth2UserInfo.getNick();
		}
		String name = oauth2UserInfo.getName();
		String tel = oauth2UserInfo.getTel();
		String email = oauth2UserInfo.getEmail();
		
		UserVO userVO = userMapper.loginCheck(email);
		if(userVO == null) {
			userVO = new UserVO();
			userVO.setEmail(email);
			userVO.setNick(username);
			userVO.setNm(name);
			userVO.setId(email);
			userVO.setPwd(pwd);
			userVO.setTel(tel);
			userVO.setPath(provider);
			
			MembVO membVO = new MembVO();
			membVO.setEmail(email);
			membVO.setNick(username);
			membVO.setNm(name);
			membVO.setId(email);
			membVO.setPwd(pwd);
			membVO.setTel(tel);
			membVO.setPath(provider);
			membMapper.signUpMemb(membVO);
			userVO.setMembNo(membVO.getMembNo());
		}
		
		return new PrincipalDetails(userVO, oauth2User.getAttributes());
		
	}

}
