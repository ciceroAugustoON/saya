package com.backend.saya.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.backend.saya.dto.ApiResponse;
import com.backend.saya.dto.RelatoryResponse;
import com.backend.saya.dto.TaskResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

	public ApiResponse getUserTasks(String token) {
		User user = getUser(token);
		if (user == null) return ApiResponse.error(HttpStatus.NOT_FOUND.name(), "Token Invalid");
		String msg = isUserDefined(user);
		if (msg != null) return ApiResponse.error(HttpStatus.NOT_FOUND.name(), msg);
		if (user.getDailyTasksDate() == null) {
			user.cleanTasks();
			TaskGenerator.generate(user, taskRepository, relatoryRepository);
			userRepository.saveAndFlush(user);
		}
		Calendar today = Calendar.getInstance();
		Calendar tomorrow = Calendar.getInstance();
		today.setTime(new Date());
		tomorrow.setTime(user.getDailyTasksDate());
		if (today.get(Calendar.DAY_OF_MONTH) > tomorrow.get(Calendar.DAY_OF_MONTH) && today.after(tomorrow)) {
			user.cleanTasks();
			TaskGenerator.generate(user, taskRepository, relatoryRepository);
			userRepository.saveAndFlush(user);
		}
		List<TaskResponse> taskResponseList = new ArrayList<>();
		for (Task t : user.getDailyTasks()) {
			taskResponseList.add(new TaskResponse(t.getId(), t.getName(), t.getDescription(), t.getDifficulty().getCode(), t.getTimeSecs(), t.getHabit().getName()));
		}
		return ApiResponse.success(taskResponseList);
	}

	public ApiResponse finishUserTask(String token, Long taskId, Integer timeSecs) {
		User user = getUser(token);
		if (user == null) return ApiResponse.error(HttpStatus.NOT_FOUND.name(), "Token Invalid");
		String msg = isUserDefined(user);
		if (msg != null) return ApiResponse.error(HttpStatus.NOT_FOUND.name(), msg);
		Task task = taskRepository.getReferenceById(taskId);
		Relatory r = user.getRelatory();
		if (r.getTasksQuantity() == user.getDailyTasks().size()) {
			r.increaseOffensive();
		}
		user.removeDailyTask(task);
		r.addPoints(task.getDifficulty().getValue());
		if (timeSecs != null) {
			r.addTimeSaved(timeSecs);
		} else {
			r.addTimeSaved(task.getTimeSecs());
		}
		if (user.getObjectives().getHabitsHad().contains(task.getHabit()) || user.getObjectives().getDesiredHabits().contains(task.getHabit())) {
			r.addTaskFinishedByHabit(task.getHabit().getId());
		}

		relatoryRepository.saveAndFlush(r);
		userRepository.saveAndFlush(user);
		return ApiResponse.success(user.getDailyTasks());
	}

	private User getUser(String token) {
		TokenAccess tokenAccess = tokenAccessRepository.findByToken(token);
		if (tokenAccess == null) return null;
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

    public ApiResponse getRelatory(String token) {
		User user = getUser(token);
		if (user == null) return ApiResponse.error(HttpStatus.NOT_FOUND.name(), "Token Invalid");
		Relatory relatory = user.getRelatory();

		RelatoryResponse relatoryResponse = new RelatoryResponse(relatory.getOffensive(), relatory.getTotalTimeSave(), relatory.getTasksFinishedByHabit());
		return ApiResponse.success(relatoryResponse);
    }
}
