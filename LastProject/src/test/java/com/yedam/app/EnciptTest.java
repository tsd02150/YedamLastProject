package com.yedam.app;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EnciptTest {	
	@Test
	public void test() {
		BCryptPasswordEncoder scpwd = new BCryptPasswordEncoder();
		String password = scpwd.encode("1234");
		System.out.println(password);
	}
}
