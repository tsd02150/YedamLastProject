package com.yedam.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.yedam.app.security.service.UserVO;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig{

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
	   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	      http
	      	 .csrf().disable()
	         .authorizeHttpRequests()
	         .antMatchers("/","/member/survey", "/stock/**", "/comunity/**").permitAll()
//	         .anyRequest().permitAll()
	         .antMatchers("/community/chat","/member/mypage").permitAll() //로그인 하고 로그인 해야 들어가는 페이지로 수정하기
//	         .anyRequest().authenticated()
	         .and()
	         .formLogin()// 로그인하는 경우에 대해 설정
	         .passwordParameter("pwd")
	         .successHandler(authenticationSuccessHandler())
	         .failureHandler(authenticationFailureHandler())
	         .loginPage("/member/login") //로그인 페이지 2개일 경우 어떻게 처리하는지
//	         .loginPage("/")
	         .permitAll()
	         .and()
	         .logout((logout) -> logout.permitAll());

	      return http.build();
	   }
}