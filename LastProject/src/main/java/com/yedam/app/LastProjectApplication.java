package com.yedam.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan(basePackages = "com.yedam.app.**.mapper")
public class LastProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(LastProjectApplication.class, args);
	}
	
}
