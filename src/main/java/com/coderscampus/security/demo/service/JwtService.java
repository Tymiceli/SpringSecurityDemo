package com.coderscampus.security.demo.service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	@Value("${jwt.signingKey}")
	private String jwtSigningKey;
	
	@Value("${jwt.expirationTimeInMillis}")
	private Long expirationTimeInMillis;
	
	public String generateToken(Map<String, Object> extraClaims, UserDetails user) {
			
		String jws = Jwts.builder()
				.claims(extraClaims)
				.issuer("Tyler Miceli")
				.subject(user.getUsername())
			    .expiration(new Date(System.currentTimeMillis() + expirationTimeInMillis)) //a java.util.Date
			    .issuedAt(new Date())
			    .signWith(getSigningKey())
			    .compact();
		return jws;
	}

	private Key getSigningKey() {
		
		byte[] jwtSigningKeyAsBytes = Decoders.BASE64.decode(jwtSigningKey);
		SecretKey secretKey = Keys.hmacShaKeyFor(jwtSigningKeyAsBytes);

		return secretKey;
	}

	public void setJwtSigningKey(String jwtSigningKey) {
		this.jwtSigningKey = jwtSigningKey;
	}

	public void setExpirationTimeInMillis(Long expirationTimeInMillis) {
		this.expirationTimeInMillis = expirationTimeInMillis;
	}
	
	
}
	
