package com.coderscampus.security.demo.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.coderscampus.security.demo.domain.RefreshToken;
import com.coderscampus.security.demo.domain.User;
import com.coderscampus.security.demo.repository.RefreshTokenRepository;
import com.coderscampus.security.demo.request.RefreshTokenRequest;

@Service
public class RefreshTokenService {

	private UserService userService;
	private RefreshTokenRepository refreshTokenRepository;
	private JwtService jwtService;
	
	@Value("${jwt.refreshTokenExpirationTimeInMillis}")
	private Long refreshTokenExpirationTimeInMillis;
	
	public RefreshTokenService(UserService userService, RefreshTokenRepository refreshTokenRepository, JwtService jwtService) {
		super();
		this.userService = userService;
		this.refreshTokenRepository = refreshTokenRepository;
		this.jwtService = jwtService;
	}


	public RefreshToken generateRefreshToken (Integer userId) {
		Optional<User> userOpt = userService.findById(userId);
		
		if (userOpt.isPresent()) {
		Optional<RefreshToken> refreshTokenOpt= refreshTokenRepository.findById(userId);
		
		RefreshToken refreshToken = null;
		
			if (refreshTokenOpt.isPresent()) {
				refreshToken = refreshTokenOpt.get();
				refreshToken.setExpiration(generateRefreshTokenExpirationDate());
				refreshToken.setRefreshToken(generateRandomTokenValue());
			} else {
				refreshToken = new RefreshToken(userOpt.get(), generateRandomTokenValue(), generateRefreshTokenExpirationDate()); 
			}
			refreshToken = refreshTokenRepository.save(refreshToken);
			return refreshToken;
		}
		return null;
	}


	private String generateRandomTokenValue() {
		return UUID.randomUUID().toString();
	}


	private Date generateRefreshTokenExpirationDate() {
		return new Date(System.currentTimeMillis() + refreshTokenExpirationTimeInMillis);
	}


	public String generateNewAccessToken(RefreshTokenRequest refreshTokenRequest) {
		Optional<RefreshToken> refreshTokenOpt = refreshTokenRepository.findByRefreshToken(refreshTokenRequest.refreshToken());
		// TODO: write code to check that the refreshtoken hasn't expired
		
		String accessToken = refreshTokenOpt.map(RefreshTokenService::isNonExpired)
				.map(refreshToken -> jwtService.generateToken(new HashMap<>(), refreshToken.getUser()))
				.orElseThrow(() -> new IllegalArgumentException("Refresh Token Not Found"));
		
		return accessToken;
	}
	
	private static RefreshToken isNonExpired (RefreshToken refreshToken) {
		if (refreshToken.getExpiration().after(new Date())){
			return refreshToken;
		} else {
			throw new IllegalArgumentException("Refresh Token has expired");
		}
	}
}
