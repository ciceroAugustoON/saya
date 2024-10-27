package com.backend.saya.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.saya.entities.Task;
import com.backend.saya.entities.TokenAccess;
import com.backend.saya.entities.User;
import com.backend.saya.repositories.RelatoryRepository;
import com.backend.saya.repositories.TaskRepository;
import com.backend.saya.repositories.TokenAccessRepository;
import com.backend.saya.repositories.UserRepository;
import com.backend.saya.util.TaskGenerator;

@Service
public class SystemService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TokenAccessRepository tokenAccessRepository;
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private RelatoryRepository relatoryRepository;

	public List<Task> getUserTasks(String hashcode) {
		TokenAccess tokenAccess = tokenAccessRepository.findByToken(hashcode);
		User user = userRepository.getReferenceById(tokenAccess.getUserId());
		if (!user.getDailyTasks().isEmpty() && user.getDailyTasksDate().equals(new Date())) {
			return user.getDailyTasks();
		}		
		user.cleanTasks();
		TaskGenerator.generate(user, taskRepository, relatoryRepository);
		return user.getDailyTasks();
	}

}
