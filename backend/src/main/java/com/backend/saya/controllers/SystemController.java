package com.backend.saya.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.backend.saya.entities.Task;
import com.backend.saya.services.SystemService;

@CrossOrigin("*")
@RestController
@RequestMapping("/user")
public class SystemController {
	@Autowired
	private SystemService systemService;
	
	@GetMapping("/tasks")
	public List<Task> getUserTasks(@RequestBody String token) {
		return systemService.getUserTasks(token);
	}
	
	@PutMapping("/tasks/{id}")
	public List<Task> finishTask(@RequestBody String token, @PathVariable Long id) {
		return systemService.finishUserTask(token, id);
	}
}
