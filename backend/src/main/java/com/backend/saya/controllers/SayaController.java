package com.backend.saya.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.backend.saya.entities.Task;
import com.backend.saya.services.SayaService;

@CrossOrigin("*")
@RestController
@RequestMapping("/user")
public class SayaController {
	@Autowired
	private SayaService sayaService;
	
	@GetMapping("/tasks")
	public List<Task> getUserTasks(@RequestHeader String token) {
		return sayaService.getUserTasks(token);
	}
	
	@PutMapping("/tasks/{id}")
	public List<Task> finishTask(@RequestHeader String token, @PathVariable Long id, @RequestParam Integer time) {
		return sayaService.finishUserTask(token, id, time);
	}
}
