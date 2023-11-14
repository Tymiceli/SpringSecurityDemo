package com.coderscampus.security.demo.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coderscampus.security.demo.domain.User;
import com.coderscampus.security.demo.repository.UserRepository;
import com.coderscampus.security.demo.service.JwtService;

@RestController
@RequestMapping ("/api/v1/users")
public class UserController {

	private PasswordEncoder passwordEncoder;
	private UserRepository userRepository;
	private JwtService jwtService;
	
	public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
	}

	@PostMapping ("")
	public ResponseEntity<User> signUpUser (@RequestBody User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		User savedUser = userRepository.save(user);
		Map<String, Object> blah = new HashMap<>();
		
		jwtService.generateToken(blah, savedUser);
		
		return ResponseEntity.ok(savedUser);	
	}

}
