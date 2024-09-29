package com.backend.saya.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.saya.entities.Task;
import com.backend.saya.repositories.TaskRepository;

@RestController
@RequestMapping("/tasks")
public class TaskController {
	@Autowired
	private TaskRepository taskRepository;
	
	public TaskController() {
		
	}
	
	@GetMapping("/")
	public List<Task> findAll() {
		return taskRepository.findAll();
	}
	@GetMapping("/{id}")
	public Task findById(@PathVariable Long id) {
		return taskRepository.findById(id).get();
	}
}
