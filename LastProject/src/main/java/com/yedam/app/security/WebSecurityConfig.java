package com.yedam.app.security;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;


import com.yedam.app.security.service.PrincipalOauth2UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig{
	
	@Autowired
	PrincipalOauth2UserService principalOauth2UserService;

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
	        	HttpSession session = request.getSession();
	        	session.setAttribute("noAccess", "접근이 거부되었습니다. 권한이 없습니다.");
	            response.sendRedirect("/");
	        };
	    }
	   
	   @Bean
	   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	      http
	      	 .csrf().disable()
	         .authorizeHttpRequests()
	         .antMatchers("/","/member/survey", "/stock/**", "/static/**").permitAll()
	         .antMatchers("/admin/**").hasRole("ADMIN")
	         .antMatchers("/community/**").hasAnyRole("ADMIN","USER")
	         .antMatchers("/member/mypage").authenticated()
	         .anyRequest().permitAll()
	         .and()
	         .formLogin() // 로그인하는 경우에 대해 설정
	         .passwordParameter("pwd")
	         .successHandler(authenticationSuccessHandler())
	         .failureHandler(authenticationFailureHandler())
	         .loginPage("/member/login")
	         .permitAll()
	         .and()
	         .logout((logout) -> logout.logoutSuccessUrl("/").logoutUrl("/member/logout").permitAll())
	         .exceptionHandling()
             .accessDeniedHandler(accessDeniedHandler())
             .and()
	     	 .oauth2Login()				// OAuth2기반의 로그인인 경우
             .loginPage("/member/login")		// 인증이 필요한 URL에 접근하면 /loginForm으로 이동
             .successHandler(authenticationSuccessHandler())		// 로그인 성공하면 "/" 으로 이동
             .failureHandler(authenticationFailureHandler())		// 로그인 실패 시 /loginForm으로 이동
             .userInfoEndpoint()			// 로그인 성공 후 사용자정보를 가져온다
             .userService(principalOauth2UserService);	//사용자정보를 처리할 때 사용한다
	      return http.build();
	   }
}