package com.yedam.app.security;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class CustomFailureHandler implements AuthenticationFailureHandler{

   @Override
   public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
         AuthenticationException exception) throws IOException, ServletException {
	   System.out.println("custom Fail");
	   HttpSession session = request.getSession();
	   session.setAttribute("message", "다시 시도해주세요");
      response.sendRedirect("/member/login");
   }
}