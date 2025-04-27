package com.backend.saya.services;

import com.backend.saya.entities.*;
import com.backend.saya.entities.enumeration.Archetype;
import com.backend.saya.entities.enumeration.Segmentation;
import com.backend.saya.repositories.HabitRepository;
import com.backend.saya.util.MathUtils;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.backend.saya.exceptions.ConflictException;
import com.backend.saya.exceptions.NotFoundException;
import com.backend.saya.repositories.ObjectivesRepository;
import com.backend.saya.repositories.TokenAccessRepository;
import com.backend.saya.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	@Autowired
	private ArchetypeService archetypeService;
	@Autowired
	private HabitRepository habitRepository;
	
	public TokenAccess login(String username, String password) {
		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			throw new IllegalArgumentException("Username and password cannot be blank");
		}

		Optional<User> userOpt = userRepository.findByUsername(username);
		if (userOpt.isEmpty()) {
			throw new NotFoundException("Invalid credentials");
		}

		User user = userOpt.get();

		if (!loginSecurity.matches(password, user.getPassword())) {
			throw new NotFoundException("Invalid credentials");
		}

		return loginSecurity.getTokenAccess(user.getId());
	}
	
	public TokenAccess register(String email, String username, String password) {
		var user = new User();
		user.setEmail(email);
		if (userRepository.exists(Example.of(user))) {
			throw new ConflictException("Email already registered");
		}
		if (userRepository.exists(Example.of(new User(null, username, null, null)))) {
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
		for (Habit h : objectives.getHabitsHad()) {
			Habit h1 = habitRepository.findByName(h.getName()).orElse(new Habit());
			objectives.removeHabitHad(h);
			objectives.addHabitHad(h1);
		}
		for (Habit h : objectives.getDesiredHabits()) {
			Habit h1 = habitRepository.findByName(h.getName()).orElse(new Habit());
			objectives.removeDesiredHabit(h);
			objectives.addDesiredHabit(h1);
		}

		objectivesRepository.saveAndFlush(objectives);
		user.setObjectives(objectives);
		archetypeService.defineArchetype(user);
		return userRepository.saveAndFlush(user);
	}


	public User addHabits(String token, Habits habits) {
		TokenAccess tokenAccess = tokenAccessRepository.findByToken(token);
		User user = userRepository.getReferenceById(tokenAccess.getUserId());
		if (user.getObjectives() == null) {
			throw new RuntimeException("User doesn't have objectives");
		}
		user.getObjectives().addAllHabitsHad(findHabitsDB(habits.getHabitsHad()));
		user.getObjectives().addAllDesiredHabits(findHabitsDB(habits.getDesiredHabits()));
		objectivesRepository.saveAndFlush(user.getObjectives());
		archetypeService.defineArchetype(user);
		return userRepository.saveAndFlush(user);
	}

	private List<Habit> findHabitsDB(Habit[] habits) {
		List<Habit> habitsDB = new ArrayList<>();
		for (Habit h : habits) {
			habitsDB.add(habitRepository.findByName(h.getName()).get());
		}
		return habitsDB;
	}

	public User removeHabits(String token, Habits habits) {
		TokenAccess tokenAccess = tokenAccessRepository.findByToken(token);
		User user = userRepository.getReferenceById(tokenAccess.getUserId());
		if (user.getObjectives() == null) {
			throw new RuntimeException("User doesn't have objectives");
		}
		user.getObjectives().getHabitsHad().removeAll(findHabitsDB(habits.getHabitsHad()));
		user.getObjectives().getDesiredHabits().removeAll(findHabitsDB(habits.getDesiredHabits()));
		objectivesRepository.saveAndFlush(user.getObjectives());
		archetypeService.defineArchetype(user);
		return userRepository.saveAndFlush(user);
	}
}
