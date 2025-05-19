package com.backend.saya.services;

import com.backend.saya.dto.ApiResponse;
import com.backend.saya.dto.ObjectivesRequest;
import com.backend.saya.dto.UserResponse;
import com.backend.saya.entities.*;
import com.backend.saya.entities.enumeration.Archetype;
import com.backend.saya.entities.enumeration.Segmentation;
import com.backend.saya.repositories.HabitRepository;
import com.backend.saya.util.MathUtils;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.backend.saya.exceptions.ConflictException;
import com.backend.saya.exceptions.NotFoundException;
import com.backend.saya.repositories.ObjectivesRepository;
import com.backend.saya.repositories.TokenAccessRepository;
import com.backend.saya.repositories.UserRepository;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.*;

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
	
	public ResponseEntity<ApiResponse> login(String identifier, String password) {
		if (StringUtils.isBlank(identifier) || StringUtils.isBlank(password)) {
			return ResponseEntity.badRequest().body(ApiResponse.error("Bad Request", "Username and Password are requested!"));
		}

		Optional<User> userOpt = userRepository.findByUsernameOrEmail(identifier);
		if (userOpt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		User user = userOpt.get();

		if (!loginSecurity.matches(password, user.getPassword())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("Unauthorized", "Invalid credentials"));
		}

		TokenAccess token = loginSecurity.getTokenAccess(user.getId());
		boolean objectivesDefined = user.getObjectives() != null;
		return ResponseEntity.ok(ApiResponse.success(new UserResponse(token.getUserId(), identifier, objectivesDefined, token.getToken())));
	}
	
	public ResponseEntity<ApiResponse> register(String email, String username, String password) {
		if (StringUtils.isBlank(email) || StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			return ResponseEntity.badRequest()
					.body(ApiResponse.error("Bad Request", "All fields are required"));
		}
		if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-z]{2,}$")) {
			return ResponseEntity.badRequest()
					.body(ApiResponse.error("Bad Request", "Invalid email format"));
		}
		if (userRepository.emailAlreadyExists(email)) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(ApiResponse.error("Conflict", "Email already registered"));
		}
		if (userRepository.usernameAlreadyExists(username)) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(ApiResponse.error("Conflict", "Username already registered"));
		}
		var user = new User(null, username, loginSecurity.encode(password), null);
		user.setEmail(email);
		user = userRepository.saveAndFlush(user);
		if (user.getId() != null) {
			TokenAccess token = loginSecurity.getTokenAccess(user.getId());
			return ResponseEntity.ok(ApiResponse.success(new UserResponse(token.getUserId(), username, false, token.getToken())));
		} else {
			return ResponseEntity.internalServerError()
					.body(ApiResponse.error("Internal Server Error", "An error occurred while processing your registration. Please try again later."));
		}
	}

	public boolean changePassword(@RequestHeader String username, String password) {
		User user = userRepository.findByUsernameOrEmail(username).orElse(null);
		if (user == null) throw new NotFoundException("User not found");
		user.setPassword(password);
		return Objects.equals(userRepository.save(user).getPassword(), password);
	}

	public ResponseEntity<ApiResponse> registerObjectives(String token, ObjectivesRequest objectivesRequest) {
		TokenAccess tokenAccess = tokenAccessRepository.findByToken(token);
		User user = userRepository.getReferenceById(tokenAccess.getUserId());
		if (user.getObjectives() != null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse.error("Conflict", "This user already has objectives defined"));
		}
		Objectives objectives = new Objectives(objectivesRequest.getDailySpendedHours(), objectivesRequest.getMetaReduction());
		objectives.addAllDesiredHabits(habitRepository.findAllById(Arrays.asList(objectivesRequest.getDesiredHabits())));
		objectives.addAllHabitsHad(habitRepository.findAllById(Arrays.asList(objectivesRequest.getHabitsHad())));

		objectivesRepository.saveAndFlush(objectives);
		user.setObjectives(objectives);
		archetypeService.defineArchetype(user);
		user = userRepository.saveAndFlush(user);
		UserResponse userResponse = new UserResponse(user.getId(), user.getUsername(), true, token);
		return ResponseEntity.ok(ApiResponse.success(userResponse));
	}


	public User addHabits(String token, Habits habits) {
		TokenAccess tokenAccess = tokenAccessRepository.findByToken(token);
		User user = userRepository.getReferenceById(tokenAccess.getUserId());
		if (user.getObjectives() == null) {
			throw new RuntimeException("User doesn't have objectives");
		}
		if (habits.getHabitsHad() != null) {
			user.getObjectives().addAllHabitsHad(findHabitsDB(habits.getHabitsHad()));
		}
		if (habits.getDesiredHabits() != null) {
			user.getObjectives().addAllDesiredHabits(findHabitsDB(habits.getDesiredHabits()));
		}

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
		if (habits.getHabitsHad() != null) {
			user.getObjectives().getHabitsHad().removeAll(findHabitsDB(habits.getHabitsHad()));
		}
		if (habits.getDesiredHabits() != null) {
			user.getObjectives().getDesiredHabits().removeAll(findHabitsDB(habits.getDesiredHabits()));
		}
		objectivesRepository.saveAndFlush(user.getObjectives());
		archetypeService.defineArchetype(user);
		return userRepository.saveAndFlush(user);
	}
}
