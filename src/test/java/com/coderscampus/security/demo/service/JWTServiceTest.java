package com.coderscampus.security.demo.service;

import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coderscampus.security.demo.domain.User;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
class JWTServiceTest {
	
	@Autowired
	private JwtService sut;
	
//	@BeforeAll
//	static void init() {
//		sut = new JwtService();
//	}

	@Test
	void testGenerateTokens() {
		
		/** 
		 * 
		 * 1. Arrange
		 * 2. Act
		 * 3. Assert
		 * 
		 */
		
		// Arrange
		HashMap<String, Object> extraClaims = new HashMap<>();
		User user = new User("tyler@coderscampus.com", "abc123");
		
		// Act
		String jwt = sut.generateToken(extraClaims, user);
		
		// Assert
		System.out.println(jwt);
	}
	
	

}
