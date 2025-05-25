package com.backend.saya.controllers;

import com.backend.saya.dto.ApiResponse;
import com.backend.saya.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.saya.services.SayaService;

@RestController
@RequestMapping("/user")
public class SayaController {
	@Autowired
	private SayaService sayaService;
	
	@GetMapping("/tasks")
	public ResponseEntity<ApiResponse> getUserTasks(@RequestHeader String token) {
		return ResponseUtil.generateResponseEntity(sayaService.getUserTasks(token));
	}
	
	@PutMapping("/tasks/{id}")
	public ResponseEntity<ApiResponse> finishTask(@RequestHeader String token, @PathVariable Long id, @RequestParam(required = false) Integer time) {
		return ResponseUtil.generateResponseEntity(sayaService.finishUserTask(token, id, time));
	}

	@GetMapping("/relatory")
	public ResponseEntity<ApiResponse> getRelatory(@RequestHeader String token) {
		return ResponseUtil.generateResponseEntity(sayaService.getRelatory(token));
	}
}
