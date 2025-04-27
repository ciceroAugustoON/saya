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
		Relatory relatory = user.getRelatory();
		user.setDailyTasksDate(new Date());
		if (relatory == null) {
			relatory = new Relatory();
			generateFirstDay(user, taskRepository);
			relatory.setTasksQuantity(user.getDailyTasks().size());
			relatoryRepository.saveAndFlush(relatory);
			user.setRelatory(relatory);
			return;
		}
		relatoryRepository.saveAndFlush(relatory);
		generateDailyTask(user, taskRepository);
		return;
	}

	private static void generateDailyTask(User user, TaskRepository taskRepository) {

	}

	private static void generateFirstDay(User user, TaskRepository taskRepository) {
		List<Task> tasksHaded = getRelationTasks(user.getObjectives().getHabitsHad(), taskRepository);
		List<Task> tasksDesired = getRelationTasks(user.getObjectives().getDesiredHabits(), taskRepository);

		user.getDailyTasks().addAll(tasksHaded.subList(0, Math.min(tasksHaded.size(), 3)));
		user.getDailyTasks().addAll(tasksDesired.subList(0, Math.min(tasksDesired.size(), 2)));
	}

	private static List<Task> getRelationTasks(List<Habit> habits, TaskRepository taskRepository) {
		List<Task> tasks = new ArrayList<Task>();
		for (Habit h : habits) {
			tasks.addAll(taskRepository.findByHabit(h));
		}
		// tasks.addAll(taskRepository.findByHabit(new Habit(1L, "Geral")));
		Collections.shuffle(tasks);
		return tasks;
	}

	private static List<Task> getTasksByDifficulty(Difficulty difficulty, List<Task> tasks) {
		return tasks.stream().filter(t -> t.getDifficulty().equals(difficulty)).toList();
	}
}
