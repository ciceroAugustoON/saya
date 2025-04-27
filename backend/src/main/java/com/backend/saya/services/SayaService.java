package com.backend.saya.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.saya.entities.Relatory;
import com.backend.saya.entities.Task;
import com.backend.saya.entities.TokenAccess;
import com.backend.saya.entities.User;
import com.backend.saya.exceptions.NotFoundException;
import com.backend.saya.repositories.RelatoryRepository;
import com.backend.saya.repositories.TaskRepository;
import com.backend.saya.repositories.TokenAccessRepository;
import com.backend.saya.repositories.UserRepository;
import com.backend.saya.repositories.WeekRepository;
import com.backend.saya.util.TaskGenerator;

@Service
public class SayaService {
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
		String msg = isUserDefined(user);
		if (msg != null) throw new NotFoundException(msg);
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

	public List<Task> finishUserTask(String token, Long taskId) {
		User user = getUser(token);
		String msg = isUserDefined(user);
		if (msg != null) throw new NotFoundException(msg);
		Task task = taskRepository.getReferenceById(taskId);
		Relatory r = user.getRelatory();
		if (r.getTasksQuantity() == user.getDailyTasks().size()) {
			r.increaseOffensive();
		}
		user.removeDailyTask(task);
		r.addPoints(task.getDifficulty().getValue());

		relatoryRepository.saveAndFlush(r);
		userRepository.saveAndFlush(user);
		return user.getDailyTasks();
	}

	private User getUser(String hashcode) {
		TokenAccess tokenAccess = tokenAccessRepository.findByToken(hashcode);
		return userRepository.getReferenceById(tokenAccess.getUserId());
	}
	
	private String isUserDefined(User user) {
		if (user.getObjectives() == null) {
			return "The user has no defined objectives!";
		}
		if (user.getObjectives().getHabitsHad() == null || user.getObjectives().getHabitsHad().isEmpty() && user.getObjectives().getDesiredHabits() == null || user.getObjectives().getDesiredHabits().isEmpty()) {
			return "The user has no defined habits";
		}
		return null;
	}

}
