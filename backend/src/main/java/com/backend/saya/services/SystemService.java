package com.backend.saya.services;

import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.saya.entities.Relatory;
import com.backend.saya.entities.Task;
import com.backend.saya.entities.TokenAccess;
import com.backend.saya.entities.User;
import com.backend.saya.repositories.RelatoryRepository;
import com.backend.saya.repositories.TaskRepository;
import com.backend.saya.repositories.TokenAccessRepository;
import com.backend.saya.repositories.UserRepository;
import com.backend.saya.repositories.WeekRepository;
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
	@Autowired
	private WeekRepository weekRepository;

	public List<Task> getUserTasks(String hashcode) {
		User user = getUser(hashcode);
		if (user.getDailyTasksDate() == null) {
			user.cleanTasks();
			TaskGenerator.generate(user, taskRepository, relatoryRepository, weekRepository);
			userRepository.saveAndFlush(user);
		}
		Calendar today = Calendar.getInstance();
		Calendar tomorrow = Calendar.getInstance();
		today.setTime(new Date());
		tomorrow.setTime(user.getDailyTasksDate());
		if (today.get(Calendar.DAY_OF_MONTH) > tomorrow.get(Calendar.DAY_OF_MONTH) && today.after(tomorrow)) {
			user.cleanTasks();
			TaskGenerator.generate(user, taskRepository, relatoryRepository, weekRepository);
			userRepository.saveAndFlush(user);
		}
		return user.getDailyTasks();
	}

	public List<Task> finishUserTask(String hashcode, Long taskId) {
		User user = getUser(hashcode);
		System.out.println(user.getDailyTasksDate());
		Task task = taskRepository.getById(taskId);
		user.removeDailyTask(task);
		Relatory r = user.getObjectives().getRelatory();
		r.addTask(task);
		relatoryRepository.saveAndFlush(r);
		userRepository.saveAndFlush(user);
		return user.getDailyTasks();
	}

	private User getUser(String hashcode) {
		TokenAccess tokenAccess = tokenAccessRepository.findByToken(hashcode);
		return userRepository.getReferenceById(tokenAccess.getUserId());
	}

}
