package com.backend.saya.services;

import com.backend.saya.dto.*;
import com.backend.saya.entities.*;
import com.backend.saya.repositories.HabitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.backend.saya.exceptions.NotFoundException;
import com.backend.saya.repositories.ObjectivesRepository;
import com.backend.saya.repositories.TokenAccessRepository;
import com.backend.saya.repositories.UserRepository;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.*;
import java.util.stream.Collectors;

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
	
	public ApiResponse login(UserRequest userRequest) {
		if ((userRequest.getUsername().isBlank() && userRequest.getEmail().isBlank()) || userRequest.getPassword().isBlank()) {
			return ApiResponse.error(HttpStatus.BAD_REQUEST.name(), "Username and Password are requested!");
		}
		String identifier = (userRequest.getUsername().isBlank())? userRequest.getEmail() : userRequest.getUsername();
		Optional<User> userOpt = userRepository.findByUsernameOrEmail(identifier);
		if (userOpt.isEmpty()) {
			return ApiResponse.error(HttpStatus.NOT_FOUND.name(), "");
		}

		User user = userOpt.get();

		if (!loginSecurity.matches(userRequest.getPassword(), user.getPassword())) {
			return ApiResponse.error(HttpStatus.UNAUTHORIZED.name(), "Invalid credentials");
		}

		TokenAccess token = loginSecurity.getTokenAccess(user.getId());
		return ApiResponse.success(new UserResponse(token.getUserId(), identifier, token.getToken()));
	}
	
	public ApiResponse register(UserRequest userRequest) {
		if (userRequest.getEmail().isBlank() || userRequest.getUsername().isBlank() || userRequest.getPassword().isBlank()) {
			return ApiResponse.error(HttpStatus.BAD_REQUEST.name(), "All fields are required");
		}
		if (!userRequest.getEmail().matches("^[\\w.-]+@[\\w.-]+\\.[a-z]{2,}$")) {
			return ApiResponse.error(HttpStatus.BAD_REQUEST.name(), "Invalid email format");
		}
		if (userRepository.emailAlreadyExists(userRequest.getEmail())) {
			return ApiResponse.error(HttpStatus.CONFLICT.name(), "Email already registered");
		}
		if (userRepository.usernameAlreadyExists(userRequest.getUsername())) {
			return ApiResponse.error(HttpStatus.CONFLICT.name(), "Username already registered");
		}
		var user = new User(null, userRequest.getUsername(), loginSecurity.encode(userRequest.getPassword()), null);
		user.setEmail(userRequest.getEmail());
		user = userRepository.saveAndFlush(user);
		if (user.getId() != null) {
			TokenAccess token = loginSecurity.getTokenAccess(user.getId());
			return ApiResponse.success(new UserResponse(token.getUserId(), userRequest.getUsername(), token.getToken()));
		} else {
			return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.name(), "An error occurred while processing your registration. Please try again later.");
		}
	}

	public ApiResponse registerObjectives(String token, ObjectivesRequest objectivesResponse) {
		TokenAccess tokenAccess = tokenAccessRepository.findByToken(token);
		User user = userRepository.getReferenceById(tokenAccess.getUserId());
		Objectives objectives = new Objectives(objectivesResponse.getDailySpendedHours(), objectivesResponse.getMetaReduction());
		objectives.addAllHabitsHad(habitRepository.findAllById(objectivesResponse.getHabitsHad()));
		objectives.addAllDesiredHabits(habitRepository.findAllById(objectivesResponse.getDesiredHabits()));
		if (user.getObjectives() != null) {
			return ApiResponse.error(HttpStatus.CONFLICT.name(), "This user already has objectives defined");
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
		user = userRepository.saveAndFlush(user);
		return ApiResponse.success(new UserResponse(user.getId(), user.getUsername(), tokenAccess.getToken()));
	}

	public ApiResponse addHabits(String token, HabitsRequest habitsRequest) {
		TokenAccess tokenAccess = tokenAccessRepository.findByToken(token);
		User user = userRepository.getReferenceById(tokenAccess.getUserId());
		if (user.getObjectives() == null) {
			return ApiResponse.error(HttpStatus.METHOD_NOT_ALLOWED.name(), "User doesn't have objectives defined");
		}
		if (habitsRequest.getHabitsHadIds() != null) {
			user.getObjectives().addAllHabitsHad(habitRepository.findAllById(habitsRequest.getHabitsHadIds()));
		}
		if (habitsRequest.getDesiredHabitsIds() != null) {
			user.getObjectives().addAllDesiredHabits(habitRepository.findAllById(habitsRequest.getDesiredHabitsIds()));
		}

		objectivesRepository.saveAndFlush(user.getObjectives());
		archetypeService.defineArchetype(user);
		return ApiResponse.success(new UserResponse(user.getId(), user.getUsername(), tokenAccess.getToken()));
	}

	public ApiResponse removeHabits(String token, HabitsRequest habitsRequest) {
		TokenAccess tokenAccess = tokenAccessRepository.findByToken(token);
		User user = userRepository.getReferenceById(tokenAccess.getUserId());
		if (user.getObjectives() == null) {
			return ApiResponse.error(HttpStatus.METHOD_NOT_ALLOWED.name(), "User doesn't have objectives defined");
		}
		if (habitsRequest.getHabitsHadIds() != null) {
			user.getObjectives().getHabitsHad().removeIf(habit -> habitsRequest.getHabitsHadIds().contains(habit.getId()));
		}
		if (habitsRequest.getDesiredHabitsIds() != null) {
			user.getObjectives().getDesiredHabits().removeIf(habit -> habitsRequest.getDesiredHabitsIds().contains(habit.getId()));
		}
		objectivesRepository.saveAndFlush(user.getObjectives());
		archetypeService.defineArchetype(user);
		user = userRepository.saveAndFlush(user);
		return ApiResponse.success(new UserResponse(user.getId(), user.getUsername(), tokenAccess.getToken()));
	}

	public boolean changePassword(@RequestHeader String username, String password) {
		User user = userRepository.findByUsernameOrEmail(username).orElse(null);
		if (user == null) throw new NotFoundException("User not found");
		user.setPassword(password);
		return Objects.equals(userRepository.save(user).getPassword(), password);
	}
}
