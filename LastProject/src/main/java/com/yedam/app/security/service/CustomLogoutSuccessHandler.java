package com.yedam.app.security.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {
        String referer = request.getHeader("referer");
        if (referer != null && !referer.isEmpty()) {
        	// 로그인이 필요한 페이지에서 로그아웃 할 경우 메인페이지로 이동
        	if(referer.equals("http://localhost/member/mypage") ||
    			referer.equals("http://localhost/community/chat") ||
    			referer.equals("http://localhost/member/mystock") ||
    			referer.equals("http://localhost/member/mystockInfo") ||
    			referer.equals("http://localhost/member/mypageIntro") ||
    			referer.equals("http://localhost/member/mypageInfo") ||
    			referer.equals("http://localhost/member/mypoint") ||
    			referer.equals("http://localhost/member/pointChargeForm") ||
    			referer.equals("http://localhost/member/myorder") 
			) {
        		response.sendRedirect("/");
        	//permit All 페이지에서 로그아웃할 경우 로그아웃 시 해당 페이지로 이동.
        	}else {
        		response.sendRedirect(referer);
        	}
        } else {
            response.sendRedirect("/member/login");
        }
    }
}