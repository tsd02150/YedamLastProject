package com.yedam.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@MapperScan(basePackages = "com.yedam.app.**.mapper")
@Controller
public class LastProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(LastProjectApplication.class, args);
	}
	
	@GetMapping("main")
	public String main() {
		return "main/layout";
	}
	
	@GetMapping("main2")
	public String main2() {
		return "domain/stock";
	}
}
