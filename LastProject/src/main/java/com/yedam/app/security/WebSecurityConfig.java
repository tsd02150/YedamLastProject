package com.yedam.app.security;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import com.yedam.app.security.service.CustomLogoutSuccessHandler;
import com.yedam.app.security.service.PrincipalOauth2UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig{
	
	@Autowired
	PrincipalOauth2UserService principalOauth2UserService;
	
	@Autowired
    private CustomLogoutSuccessHandler logoutSuccessHandler;
	
	@Bean   //== @Component
	   PasswordEncoder passwordEncoder() {
	      return new BCryptPasswordEncoder();
	   }
	   
	   @Bean
	   CustomFailureHandler authenticationFailureHandler() {
	      return new CustomFailureHandler();
	   }
	   
	   @Bean
	   CustomSuccessHandler authenticationSuccessHandler() {
	      return new CustomSuccessHandler();
	   }
	   
	   @Bean
	   public AccessDeniedHandler accessDeniedHandler() {
	       return (request, response, accessDeniedException) -> {
	           RequestCache requestCache = new HttpSessionRequestCache();
	           RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	           SavedRequest savedRequest = requestCache.getRequest(request, response);
	           HttpSession session = request.getSession();

	           String uri = "/"; // 메인페이지

	           System.out.println(request.getHeader("referer"));
	           if (request.getHeader("referer") != null && !request.getHeader("referer").isEmpty()) {
	               uri = request.getHeader("referer");
	           }
	           if (savedRequest != null) {
	               redirectStrategy.sendRedirect(request, response, savedRequest.getRedirectUrl());
	               requestCache.removeRequest(request, response);
	           } else {
	               redirectStrategy.sendRedirect(request, response, uri);
	           }
	           if (accessDeniedException instanceof AccessDeniedException) {
	               session.setAttribute("noAccess", "접근이 거부되었습니다. 권한이 없습니다.");
	           } else {
	               session.removeAttribute("noAccess"); 
	           }
	       };
	   }
	   
	   @Bean
	   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	      http.csrf().disable()
	          .authorizeHttpRequests()
	          .antMatchers("/", "/member/mysurvey","/stock/**", "/static/**").permitAll()
	          .antMatchers("/admin/**").hasRole("ADMIN")
	          .antMatchers("/community/chat").hasAnyRole("ADMIN","USER")
	          .antMatchers("/member/mypage", "/member/mystock","/member/mystockInfo","/member/mypageIntro"
         		 		 ,"/member/mypageInfo","/member/mypoint","/member/pointChargeForm","/member/myorder"
        		 		 ,"/mall/orderList","/mall/basketList").authenticated()
	          .anyRequest().permitAll().and()
 		      .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
 		      .and()
 		      // 일반 로그인
	          .formLogin()
  	          .passwordParameter("pwd")
	          .successHandler(authenticationSuccessHandler())
	          .failureHandler(authenticationFailureHandler())
	          .loginPage("/member/login").permitAll()
	          .and()
	          .logout(logout -> logout.logoutSuccessHandler(logoutSuccessHandler)
              .logoutUrl("/member/logout").permitAll())
	          .exceptionHandling()
              .accessDeniedHandler(accessDeniedHandler())
              .and()
              // OAuth2 API 로그인
	      	  .oauth2Login()				
              .loginPage("/member/login")		
              .successHandler(authenticationSuccessHandler())		
              .failureHandler(authenticationFailureHandler())		
              .userInfoEndpoint()			
              .userService(principalOauth2UserService);	
	      return http.build();
	   }
	   

	   
}