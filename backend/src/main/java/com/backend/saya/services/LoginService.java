package com.backend.saya.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.backend.saya.entities.User;
import com.backend.saya.exceptions.ConflictException;
import com.backend.saya.exceptions.NotFoundException;
import com.backend.saya.repositories.UserRepository;

@Service
public class LoginService {
	@Autowired
	private UserRepository userRepository;
	
	public String login(String username, String password) {
		var user = new User(null, username, password);
		user = userRepository.findOne(Example.of(user)).get();
		if (user != null) {
			return "token-access";
		} else {
			throw new NotFoundException("User not found");
		}
	}
	
	public String register(String email, String username, String password) {
		var user = new User();
		user.setEmail(email);
		if (userRepository.exists(Example.of(user))) {
			throw new ConflictException("Email already registered");
		}
		user.setUsername(username);
		user.setPassword(password);
		user = userRepository.save(user);
		if (user != null) {
			return "token-access";
		} else {
			throw new RuntimeException("Error in user register");
		}
	}
	
}
