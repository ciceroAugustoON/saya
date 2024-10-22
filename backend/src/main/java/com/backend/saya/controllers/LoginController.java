package com.backend.saya.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.saya.entities.Objectives;
import com.backend.saya.entities.TokenAccess;
import com.backend.saya.entities.User;
import com.backend.saya.services.LoginService;

@RestController
@RequestMapping("/access")
public class LoginController {
	@Autowired
	private LoginService loginService;
	
	@GetMapping(value = "/login", consumes = "application/json")
	public TokenAccess login(@RequestBody User user) {
		return loginService.login(user.getUsername(), user.getPassword());
	}
	@PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
	public TokenAccess register(@RequestBody User user, Objectives objectives) {
		return loginService.register(user.getEmail(), user.getUsername(), user.getPassword(), objectives);
	}
}
