package com.coderscampus.security.demo.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;

@Service
public class JwtService {
	
	@Value("jwt.signingKey")
	private String jwtSigningKey;
	
	@Value("jwt.expirationTimeInMillis")
//	private Long expirationTimeInMillis;

	public String generateToken(HashMap<String, Object> extraClaims, UserDetails user) {
			
		String jws = Jwts.builder()
				.claims(extraClaims)
				.issuer("Tyler Miceli")
				.subject(user.getUsername())
//			    .expiration(new Date().getTime() + expirationTimeInMillis) //a java.util.Date
			    .issuedAt(new Date())
			    .signWith(getSigningKey())
			    .compact();
	
		return jws;
	}

	private Key getSigningKey() {

		SecretKey jwtSigningKey1 = Jwts.SIG.HS256.key().build();
		
		return jwtSigningKey1;
	}
}
	
