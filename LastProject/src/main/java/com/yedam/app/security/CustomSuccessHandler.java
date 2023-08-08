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
	    HttpSession session = request.getSession();
	    String referer = (String) session.getAttribute("returnUrl");

	    if (authentication != null && authentication.getPrincipal() instanceof PrincipalDetails) {
	        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
	        UserVO userVO = principalDetails.getUserVO();

	        if (userVO != null) {
	            if (userVO.getTempPwd() == null && !userVO.getNm().equals("admin")) {
	                session.setAttribute("loggedInMember", userVO);
	                response.sendRedirect(referer);
	            } else if (userVO.getTempPwd() != null && !userVO.getNm().equals("admin")) {
	                session.setAttribute("loggedInMember", userVO);
	                response.sendRedirect("/member/tempPwdUpdate");
	            } else if (userVO.getNm().equals("admin")) {
	                session.setAttribute("loggedInMember", userVO);
	                response.sendRedirect("/admin/memberManage");
	            }
	        } else {
	            response.sendRedirect("/login?error=authentication");
	        }
	    } else {
	        response.sendRedirect("/login?error=authentication");
	    }
	}
}