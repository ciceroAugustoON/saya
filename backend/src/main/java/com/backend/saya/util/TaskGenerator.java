package com.backend.saya.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.backend.saya.entities.Habit;
import com.backend.saya.entities.Relatory;
import com.backend.saya.entities.Task;
import com.backend.saya.entities.User;
import com.backend.saya.entities.Week;
import com.backend.saya.entities.enumeration.Difficulty;
import com.backend.saya.entities.enumeration.WeekDay;
import com.backend.saya.repositories.RelatoryRepository;
import com.backend.saya.repositories.TaskRepository;
import com.backend.saya.repositories.WeekRepository;

@Component
public class TaskGenerator {
	private static RelatoryRepository relatoryRepository;

	public static void generate(User user, TaskRepository taskRepository, RelatoryRepository relatoryRepository,
			WeekRepository weekRepository) {
		TaskGenerator.relatoryRepository = relatoryRepository;
		Relatory relatory = user.getObjectives().getRelatory();
		user.setDailyTasksDate(new Date());
		if (relatory == null) {
			relatory = new Relatory();
			relatory.setWeekDay(WeekDay.ONE);
			generateFirstWeek(user, taskRepository, relatory);
			return;
		}
		if (relatory.getLastWeekPoints() == null && relatory.getWeekDay() < 7) {
			generateFirstWeek(user, taskRepository, relatory);
			return;
		}
		if (user.getWeek() == null || relatory.getWeekDay() > 6) {
			generateWeek(user, weekRepository);
			relatory.setWeekDay(WeekDay.ONE);
		} else {
			relatory.setWeekDay(WeekDay.getWeekDay(relatory.getWeekDay() + 1));
		}
		relatoryRepository.saveAndFlush(relatory);
		generateDailyTask(user, taskRepository);
		return;
	}

	private static void generateDailyTask(User user, TaskRepository taskRepository) {
		int day = user.getObjectives().getRelatory().getWeekDay();
		if (user.getDailyTasksDate().before(new Date()) && user.getDailyTasksDate().getDay() < new Date().getDay()) {
			List<Task> tasks = getRelationTasks(user, taskRepository);
			List<Task> tasksProcessing = new ArrayList<>();
			tasksProcessing.addAll(getLimitedTasks(getTasksByDifficulty(Difficulty.EASY, tasks),
					user.getWeek().getEasyTasksPerDay()[day]));
			tasksProcessing.addAll(getLimitedTasks(getTasksByDifficulty(Difficulty.MEDIUM, tasks),
					user.getWeek().getMediumTasksPerDay()[day]));
			tasksProcessing.addAll(getLimitedTasks(getTasksByDifficulty(Difficulty.HARD, tasks),
					user.getWeek().getHardTasksPerDay()[day]));
			user.getDailyTasks().addAll(tasksProcessing);
		}
	}

	private static void generateWeek(User user, WeekRepository weekRepository) {
		Week week = new Week();
		Integer userPoints = user.getObjectives().getRelatory().getLastWeekPoints();
		Integer points = (userPoints == null) ? getFirstWeekPoints(user.getDailyTasks().size()) : userPoints;
		for (int i = 0; i < 7 && points > 0; i++) {
			WeekDay day = WeekDay.getWeekDay(i);
			int randomNumber = (int) (Math.round(Math.random()) * 5);
			int total = randomNumber;
			week.setEasyTasksPerDay(day, randomNumber);
			if (total < 5) {
				randomNumber = (int) (Math.round(Math.random()) * 5) - total;
				week.setMediumTasksPerDay(day, (randomNumber >= 0) ? randomNumber : 0);
				total += randomNumber;
				if (total < 5) {
					randomNumber = (int) (Math.round(Math.random()) * 5) - total;
					week.setHardTasksPerDay(day, (randomNumber >= 0) ? randomNumber : 0);
					total += randomNumber;
				}
			}
			points -= week.getValuePoints();
		}
		weekRepository.save(week);
		user.setWeek(week);
	}

	private static void generateFirstWeek(User user, TaskRepository taskRepository, Relatory relatory) {
		List<Task> tasks = getRelationTasks(user, taskRepository);
		relatory.setWeekDay(WeekDay.getWeekDay(relatory.getWeekDay() + 1));
		relatoryRepository.save(relatory);
		user.getObjectives().setRelatory(relatory);
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

	private static Integer getFirstWeekPoints(Integer totalTasks) {
		switch (totalTasks) {
		case 2: {
			return 20;
		}
		case 3: {
			return 30;
		}
		case 4: {
			return 40;
		}
		case 5: {
			return 50;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + totalTasks);
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

	private static List<Task> getTasksByDifficulty(Difficulty difficulty, List<Task> tasks) {
		return tasks.stream().filter(t -> t.getDifficulty().equals(difficulty)).toList();
	}
}
