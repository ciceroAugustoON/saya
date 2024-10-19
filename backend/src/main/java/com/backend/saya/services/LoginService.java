package com.backend.saya.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.backend.saya.entities.User;
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
			return "Success";
		} else {
			throw new NotFoundException("User not found");
		}
	}
	
	
}
