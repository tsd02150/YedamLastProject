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
	
	//private LoginHistervice loginHistService;


	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		 System.out.println("success handler 실행");
	      HttpSession session = request.getSession();
	      PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
	      UserVO userVO = principalDetails.getUserVO();
	      
    	  if(userVO.getTempPwd() == null) {
    		  session.setAttribute("loggedInMember", userVO);  
    		  response.sendRedirect("/");
    	  } else if(userVO.getTempPwd() != null){
    		  session.setAttribute("loggedInMember", userVO);  
    		  response.sendRedirect("/member/tempPwdUpdate");
    	  } else if(userVO.getNm() == "admin") {
    		  session.setAttribute("loggedInMember", userVO);  
    		  response.sendRedirect("/admin/memberManage");
    	  }
	   }
	}