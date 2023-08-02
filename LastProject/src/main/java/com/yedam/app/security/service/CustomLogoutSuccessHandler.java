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
        	if(referer.equals("http://43.202.20.221:83/member/mypage") ||
    			referer.equals("http://43.202.20.221:83/community/chat") ||
    			referer.equals("http://43.202.20.221:83/mall/orderList") ||
    			referer.equals("http://43.202.20.221:83/mall/basketList") ||
    			referer.equals("http://43.202.20.221:83/member/mystock") ||
    			referer.equals("http://43.202.20.221:83/member/mystockInfo") ||
    			referer.equals("http://43.202.20.221:83/member/mypageIntro") ||
    			referer.equals("http://43.202.20.221:83/member/mypageInfo") ||
    			referer.equals("http://43.202.20.221:83/member/mypoint") ||
    			referer.equals("http://43.202.20.221:83/member/pointChargeForm") ||
    			referer.equals("http://43.202.20.221:83/member/myorder") 
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