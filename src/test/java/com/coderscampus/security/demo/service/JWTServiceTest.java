package com.coderscampus.security.demo.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;

import com.coderscampus.security.demo.domain.User;

import io.jsonwebtoken.Claims;

@TestInstance(Lifecycle.PER_CLASS)
//@SpringBootTest
class JWTServiceTest {
	
	@Autowired
	private JwtService sut;
	
	@BeforeAll        
	void init() {
		sut = new JwtService();
		sut.setExpirationTimeInMillis(300000L);
		sut.setJwtSigningKey("CF11BDCC1AFBE161A2AD509AB09F2D025B970547765DD3C9343F78BA0A7F01D4");
	}

	@Test
	@DisplayName("should generate token")
	void testGenerateTokens() {
		
		/** 
		 * 
		 * 1. Arrange
		 * 2. Act
		 * 3. Assert
		 * 
		 */
		
		// Arrange
		Map<String, Object> extraClaims = new HashMap<>();
		User user = new User("tyler@coderscampus.com", "abc123");
		
		// Act
		String jwt = sut.generateToken(extraClaims, user);
		
		// Assert
		System.out.println(jwt);
		
		
		assertTrue(jwt.startsWith("ey"));
		
	}
	
	@Test
	@DisplayName("should extract all claims")
	void testExtractAllClaims () {
		
		Map<String, Object> extraClaims = new HashMap<>();
		User user = new User("tyler@coderscampus.com", "abc123");
		
		// Act
		String jwt = sut.generateToken(extraClaims, user);
		
		Claims claims = sut.extractAllClaims(jwt);
		
		System.out.println(claims);
		assertEquals(claims.getSubject(), user.getUsername());
		assertEquals(claims.size(), 4); // Not the best test, perhaps the test should only ask if the size of all
										// claims should be greater than 3
		assertTrue(claims.size() > 3);
	}
	
	@Test
	@DisplayName("should extract valid subject from claims")
	void testExtractSubjectClaims () {
		
		Map<String, Object> extraClaims = new HashMap<>();
		User user = new User("tyler@coderscampus.com", "abc123");
		
		// Act
		String jwt = sut.generateToken(extraClaims, user);
		
		String subject = sut.getSubject(jwt);
		
		assertEquals(subject, user.getUsername());

	}
	
	@Test
	@DisplayName("should return valid token")
	void testValidToken () {
		
		Map<String, Object> extraClaims = new HashMap<>();
		User user = new User("tyler@coderscampus.com", "abc123");
		
		// Act
		String jwt = sut.generateToken(extraClaims, user);
		
		Boolean isValidToken = sut.isValidToken(jwt, user);
		
		assertTrue(isValidToken);

	}
	
	@Test
	@DisplayName("should return invalid token")
	void testInvalidToken () {
		
		Map<String, Object> extraClaims = new HashMap<>();
		User validUser = new User("tyler@coderscampus.com", "abc123");
		User invalidUser = new User("tyler.miceli@coderscampus.com", "abc123");
		
		// Act
		String jwt = sut.generateToken(extraClaims, validUser);
		
		Boolean isValidToken = sut.isValidToken(jwt, invalidUser);
		
		assertFalse(isValidToken);
	}

}
