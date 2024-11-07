package com.backend.saya.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.saya.entities.Habit;
import com.backend.saya.entities.Objectives;
import com.backend.saya.entities.TokenAccess;
import com.backend.saya.entities.User;
import com.backend.saya.services.LoginService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/access")
public class LoginController {
	@Autowired
	private LoginService loginService;
	
	@GetMapping(value = "/login", produces = "application/json")
	public TokenAccess login(@RequestParam("username") String username, @RequestParam("password") String password) {
		return loginService.login(username, password);
	}
	@PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
	public TokenAccess register(@RequestBody User user) {
		return loginService.register(user.getEmail(), user.getUsername(), user.getPassword());
	}
	@PostMapping("/{hashcode}/objectives")
	public void registerObjectives(@PathVariable String hashcode, @RequestBody Objectives objectives) {
		loginService.registerObjectives(hashcode, objectives);
	}
	@PostMapping("/{hashcode}/habits")
	public void registerHabits(@PathVariable String hashcode, @RequestBody Habit[] habits) {
		loginService.registerHabits(hashcode, habits);
	}
	
}
