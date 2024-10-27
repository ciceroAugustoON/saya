package com.backend.saya.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.saya.entities.Task;
import com.backend.saya.repositories.TaskRepository;
import com.backend.saya.repositories.TokenAccessRepository;
import com.backend.saya.repositories.UserRepository;

@RestController
@RequestMapping("/{hashcode}")
public class SystemController {
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private TokenAccessRepository tokenAccessRepository;
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/tasks")
	public List<Task> findAll(@PathVariable(name = "hashcode") String hash) {
		return null;
	}
}
