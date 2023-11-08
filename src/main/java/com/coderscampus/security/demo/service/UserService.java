package com.coderscampus.security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.coderscampus.security.demo.domain.User;
import com.coderscampus.security.demo.repository.UserRepository;

@Service
public class UserService implements UserDetailsService{
	
	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByUsername(username);
		
			if (user == null) throw new UsernameNotFoundException("Bad Cred Bucko");
		
		return user;
	}

	
	
}