package com.backend.saya.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.backend.saya.entities.Habit;
import com.backend.saya.entities.Objectives;
import com.backend.saya.entities.TokenAccess;
import com.backend.saya.entities.User;
import com.backend.saya.services.LoginService;

@CrossOrigin("*")
@RestController
@RequestMapping("/access")
public class LoginController {
	@Autowired
	private LoginService loginService;
	
	@PostMapping(value = "/login", consumes = "application/json")
	public TokenAccess login(@RequestBody User user) {
		return loginService.login(user.getUsername(), user.getPassword());
	}
	@PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
	public TokenAccess register(@RequestBody User user) {
		return loginService.register(user.getEmail(), user.getUsername(), user.getPassword());
	}
	@PostMapping("/register/objectives")
	public User registerObjectives(@RequestHeader String token, @RequestBody Objectives objectives) {
		return loginService.registerObjectives(token, objectives);
	}
	@PostMapping("/register/habits")
	public User registerHabits(@RequestHeader String token, @RequestBody Habit[] habitsHad, @RequestBody Habit[] desiredHabits) {
		return loginService.registerHabits(token, habitsHad, desiredHabits);
	}
	
}
