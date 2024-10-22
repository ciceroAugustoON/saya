package com.backend.saya.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.saya.entities.Task;
import com.backend.saya.entities.TokenAccess;
import com.backend.saya.entities.User;
import com.backend.saya.repositories.TaskRepository;
import com.backend.saya.repositories.TokenAccessRepository;
import com.backend.saya.repositories.UserRepository;
import com.backend.saya.services.TaskGenerator;

@RestController
@RequestMapping("/tasks")
public class TaskController {
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private TokenAccessRepository tokenAccessRepository;
	@Autowired
	private UserRepository userRepository;
	
	public TaskController() {
		
	}
	
	@GetMapping("/{hashcode}")
	public List<Task> findAll(@PathVariable(name = "hashcode") String hash) {
		TokenAccess tokenAccess = new TokenAccess(null, hash);
		if (tokenAccessRepository.exists(Example.of(tokenAccess))) {
			tokenAccess = tokenAccessRepository.findOne(Example.of(tokenAccess)).get();
			if (tokenAccess.isValid()) throw new RuntimeException("Token invalid!");
			User user = userRepository.findOne(Example.of(new User(tokenAccess.getUserId(), null, null))).get();
			TaskGenerator.generate(user, taskRepository);
			return user.getDailyTasks();
		}
		return taskRepository.findAll();
	}
}
