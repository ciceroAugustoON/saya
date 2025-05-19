package com.backend.saya.controllers;

import java.util.List;

import com.backend.saya.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.saya.entities.Habit;
import com.backend.saya.repositories.HabitRepository;

@RestController
@RequestMapping("/habits")
public class HabitsController {
	@Autowired
	private HabitRepository habitRepository;
	
	@GetMapping
	public ResponseEntity<ApiResponse> findAll() {
		return ResponseEntity.ok(ApiResponse.success(habitRepository.findAll()));
	}
}
