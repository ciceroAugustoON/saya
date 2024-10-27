package com.backend.saya.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@GetMapping("")
	public List<Habit> findAll() {
		return habitRepository.findAll();
	}
}
