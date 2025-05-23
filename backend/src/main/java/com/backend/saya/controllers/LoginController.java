package com.backend.saya.controllers;

import com.backend.saya.dto.*;
import com.backend.saya.entities.*;
import com.backend.saya.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.saya.services.LoginService;

@RestController
@RequestMapping("/access")
public class LoginController {
	@Autowired
	private LoginService loginService;
	
	@PostMapping(value = "/login", consumes = "application/json")
	public ResponseEntity<ApiResponse> login(@RequestBody UserRequest userRequest) {
		return ResponseUtil.generateResponseEntity(loginService.login(userRequest));
	}
	@PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
	public ResponseEntity<ApiResponse> register(@RequestBody UserRequest userRequest) {
		return ResponseUtil.generateResponseEntity(loginService.register(userRequest));
	}
	@PostMapping("/register/objectives")
	public ResponseEntity<ApiResponse> registerObjectives(@RequestHeader String token, @RequestBody ObjectivesRequest objectives) {
		return ResponseUtil.generateResponseEntity(loginService.registerObjectives(token, objectives));
	}
	@PostMapping("/register/habits")
	public ResponseEntity<ApiResponse> addHabits(@RequestHeader String token, @RequestBody HabitsRequest habitsRequest) {
		return ResponseUtil.generateResponseEntity(loginService.addHabits(token, habitsRequest));
	}
	@PutMapping("/register/habits")
	public ResponseEntity<ApiResponse> removeHabits(@RequestHeader String token, @RequestBody HabitsRequest habitsRequest) {
		return ResponseUtil.generateResponseEntity(loginService.removeHabits(token, habitsRequest));
	}

}
