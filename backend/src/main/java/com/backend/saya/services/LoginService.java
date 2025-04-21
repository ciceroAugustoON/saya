package com.backend.saya.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.backend.saya.entities.Habit;
import com.backend.saya.entities.Objectives;
import com.backend.saya.entities.TokenAccess;
import com.backend.saya.entities.User;
import com.backend.saya.exceptions.ConflictException;
import com.backend.saya.exceptions.NotFoundException;
import com.backend.saya.repositories.ObjectivesRepository;
import com.backend.saya.repositories.TokenAccessRepository;
import com.backend.saya.repositories.UserRepository;

@Service
public class LoginService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TokenAccessRepository tokenAccessRepository;
	@Autowired
	private LoginSecurity loginSecurity;
	@Autowired
	private ObjectivesRepository objectivesRepository;
	
	public TokenAccess login(String username, String password) {
		var user = new User(null, username, loginSecurity.encode(password));
		if (userRepository.exists(Example.of(user))) {
			user = userRepository.findOne(Example.of(user)).get();
			return loginSecurity.getTokenAccess(user.getId());
		} else {
			throw new NotFoundException("User not found");
		}
	}
	
	public TokenAccess register(String email, String username, String password) {
		var user = new User();
		user.setEmail(email);
		if (userRepository.exists(Example.of(user))) {
			throw new ConflictException("Email already registered");
		}
		if (userRepository.exists(Example.of(new User(null, username, null)))) {
			throw new ConflictException("Username already registered");
		}
		user.setUsername(username);
		user.setPassword(loginSecurity.encode(password));
		user = userRepository.saveAndFlush(user);
		if (user.getId() != null) {
			return loginSecurity.getTokenAccess(user.getId());
		} else {
			throw new RuntimeException("Error in user register");
		}
	}

	public User registerObjectives(String token, Objectives objectives) {
		TokenAccess tokenAccess = tokenAccessRepository.findByToken(token);
		User user = userRepository.getReferenceById(tokenAccess.getUserId());
		if (user.getObjectives() != null) {
			throw new ConflictException("This user already has objectives defined");
		}
		objectivesRepository.saveAndFlush(objectives);
		user.setObjectives(objectives);
		return userRepository.saveAndFlush(user);
		
	}

	public User registerHabits(String token, Habit[] habitsHad, Habit[] desiredHabits) {
		TokenAccess tokenAccess = tokenAccessRepository.findByToken(token);
		User user = userRepository.getReferenceById(tokenAccess.getUserId());
		if (user.getObjectives() == null) {
			throw new RuntimeException("User doesn't have objectives");
		}
		if (!user.getObjectives().getHabitsHad().isEmpty() || !user.getObjectives().getDesiredHabits().isEmpty()) {
			throw new ConflictException("User already has habits defined");
		}
		user.getObjectives().addAllHabitsHad(habitsHad);
		user.getObjectives().addAllDesiredHabits(desiredHabits);
		objectivesRepository.saveAndFlush(user.getObjectives());
		return userRepository.saveAndFlush(user);
	}
}
