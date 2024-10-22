package com.backend.saya.services;

import java.util.List;

import org.springframework.data.domain.Example;

import com.backend.saya.entities.Habit;
import com.backend.saya.entities.Task;
import com.backend.saya.entities.User;
import com.backend.saya.repositories.TaskRepository;

public class TaskGenerator {
	
	public static void generate(User user, TaskRepository taskRepository) {
		for (Habit habit : user.getObjectives().getHabits()) {
			Task task = new Task();
			task.setHabit(habit);
			List<Task> tasks = taskRepository.findAll(Example.of(task));
			user.addDailyTask(tasks.getFirst());
		}
	}
}
