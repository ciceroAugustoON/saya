package com.backend.saya.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.backend.saya.entities.Habit;
import com.backend.saya.entities.Relatory;
import com.backend.saya.entities.Task;
import com.backend.saya.entities.User;
import com.backend.saya.entities.enumeration.WeekDay;
import com.backend.saya.repositories.RelatoryRepository;
import com.backend.saya.repositories.TaskRepository;

public class TaskGenerator {
	private static RelatoryRepository relatoryRepository;

	public static void generate(User user, TaskRepository taskRepository, RelatoryRepository relatoryRepository) {
		TaskGenerator.relatoryRepository = relatoryRepository;
		Relatory relatory = user.getObjectives().getRelatory();
		if (relatory == null) {
			relatory = new Relatory();
			relatory.setWeekDay(WeekDay.ONE);
			generateFirstWeek(user, taskRepository, relatory);
			return;
		}
		if (relatory.getLastWeekPoints() == null) {
			generateFirstWeek(user, taskRepository, relatory);
		}

	}

	private static void generateFirstWeek(User user, TaskRepository taskRepository, Relatory relatory) {
		List<Task> tasks = getRelationTasks(user, taskRepository);
		if (relatory.getWeekDay() == 0) {
			user.addDailyTask(tasks.getFirst());
			user.addDailyTask(tasks.getLast());
			relatory.setWeekDay(WeekDay.TWO);
			relatoryRepository.save(relatory);
			user.getObjectives().setRelatory(relatory);
			return;
		}
		switch (relatory.getTotalTasks()) {
		case 2: {
			user.getDailyTasks().addAll(getLimitedTasks(tasks, 3));
			break;
		}
		case 3: {
			user.getDailyTasks().addAll(getLimitedTasks(tasks, 4));
			break;
		}
		case 4: {
			user.getDailyTasks().addAll(getLimitedTasks(tasks, 5));
			break;
		}
		case 5: {
			user.getDailyTasks().addAll(getLimitedTasks(tasks, 5));
			break;
		}
		default:
			user.getDailyTasks().addAll(getLimitedTasks(tasks, 2));
			break;
		}
	}

	private static List<Task> getRelationTasks(User user, TaskRepository taskRepository) {
		List<Task> tasks = new ArrayList<Task>();
		for (Habit h : user.getObjectives().getHabits()) {
			tasks.addAll(taskRepository.findByHabit(h));
		}
		tasks.addAll(taskRepository.findByHabit(new Habit(1L, "Geral")));
		Collections.shuffle(tasks);
		return tasks;
	}

	private static List<Task> getLimitedTasks(List<Task> tasks, int limit) {
		List<Task> tasksLimited = new ArrayList<Task>();
		int listSize = tasks.size();
		for (int i = 0; i < limit && i < listSize; i++) {
			tasksLimited.add(tasks.get(i));
		}
		return tasksLimited;
	}
}
