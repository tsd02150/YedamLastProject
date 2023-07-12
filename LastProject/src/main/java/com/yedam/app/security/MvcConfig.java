package com.yedam.app.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("main/main");
		registry.addViewController("/member/login").setViewName("member/loginForm");
//		registry.addViewController("/member/findid").setViewName("member/findIdForm");
	}
}