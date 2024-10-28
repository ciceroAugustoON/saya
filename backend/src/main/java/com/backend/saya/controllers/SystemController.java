package com.backend.saya.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.saya.entities.Task;
import com.backend.saya.services.SystemService;

@RestController
@RequestMapping("/{hashcode}")
public class SystemController {
	@Autowired
	private SystemService systemService;
	
	@GetMapping("/tasks")
	public List<Task> getUserTasks(@PathVariable(name = "hashcode") String hashcode) {
		return systemService.getUserTasks(hashcode);
	}
	
	@PutMapping("/tasks/{id}")
	public List<Task> finishTask(@PathVariable String hashcode, @PathVariable Long id) {
		return systemService.finishUserTask(hashcode, id);
	}
}
