package com.yedam.app.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.yedam.app.security.service.PrincipalDetails;
import com.yedam.app.security.service.UserService;
import com.yedam.app.security.service.UserVO;

public class CustomSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	UserService userService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		 System.out.println("success handler 실행");
	      HttpSession session = request.getSession();
	      PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
	      System.out.println(principalDetails);
	      UserVO userVO = principalDetails.getUserVO();
	      System.out.println("---------------------------------");
	      System.out.println(userVO);
    	  System.out.println("임시비밀번호"+userVO.getTempPwd());
    	  if(userVO.getTempPwd() == null) {
    		  session.setAttribute("loggedInMember", userVO);  
    		  response.sendRedirect("/");
    	  } else {
    		  session.setAttribute("loggedInMember", userVO);  
    		  response.sendRedirect("/member/tempPwdUpdate");
    	  }
	   }
	}