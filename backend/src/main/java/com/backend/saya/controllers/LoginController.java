package com.backend.saya.controllers;

import com.backend.saya.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
	public User addHabits(@RequestHeader String token, @RequestBody Habits habits) {
		return loginService.addHabits(token, habits);
	}
	@PutMapping("/register/habits")
	public User removeHabits(@RequestHeader String token, @RequestBody Habits habits) {
		return loginService.removeHabits(token, habits);
	}
	
}
