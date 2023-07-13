package com.yedam.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
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
<<<<<<< HEAD
	         .antMatchers("/","/member/survey", "/stock/**","/comunity/**","../static/**").permitAll()
//	         .antMatchers("/community/chat")
	         //.authenticated().anyRequest().authenticated()
=======
	         .antMatchers("/","/member/survey", "/stock/**", "/comunity/**","/static/**").permitAll()
	         .antMatchers("/member/mypage").authenticated()
//	         .anyRequest().authenticated()
>>>>>>> branch 'main' of https://github.com/tsd02150/YedamLastProject.git
	         .anyRequest().permitAll()
	         .and()
	         .formLogin() // 로그인하는 경우에 대해 설정
	         //.loginProcessingUrl("/member/login")
	         //.usernameParameter("id")
	         .passwordParameter("pwd")
	         .successHandler(authenticationSuccessHandler())
	         .failureHandler(authenticationFailureHandler())
	         .loginPage("/member/login")
//	         .loginPage("/")
	         .permitAll()
	         .and()
	         .logout((logout) -> logout.permitAll());
	      return http.build();
	   }
}