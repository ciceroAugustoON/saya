package com.backend.saya.util;

import java.util.*;
import java.util.stream.Collectors;

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

	public static void generate(User user, TaskRepository taskRepository, RelatoryRepository relatoryRepository) {
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
		generateDailyTask(user, taskRepository, relatory);
		relatoryRepository.saveAndFlush(relatory);
	}

	private static void generateDailyTask(User user, TaskRepository taskRepository, Relatory relatory) {
		Integer objective = relatory.getTimeSave() * (user.getObjectives().getMetaReduction() * 3600 / 8);

		List<Task> tasksHaded = getRelationTasks(user.getObjectives().getHabitsHad(), taskRepository);
		List<Task> tasksDesired = getRelationTasks(user.getObjectives().getDesiredHabits(), taskRepository);
		List<Task> tasks = new ArrayList<>();
		tasks.addAll(tasksHaded);
		tasks.addAll(tasksDesired);
		List<Task> easyTasks = getTasksByDifficulty(Difficulty.EASY, tasks);
		int easyTaksMedia = mediaTasksTempo(easyTasks);
		List<Task> mediumTasks = getTasksByDifficulty(Difficulty.MEDIUM, tasks);
		int mediumTaksMedia = mediaTasksTempo(mediumTasks);
		List<Task> hardTasks = getTasksByDifficulty(Difficulty.HARD, tasks);
		int hardTaksMedia = mediaTasksTempo(hardTasks);

		List<int[]> combinations = findCombinations(easyTaksMedia, mediumTaksMedia, hardTaksMedia, objective, 10);
		Collections.shuffle(combinations);
		int[] sortedCombination = combinations.getFirst();
		// for a while the differnce between habitsHad and habitsDesired will not be considered
		Collections.shuffle(tasks);
		user.getDailyTasks().addAll(tasks.stream()
				.filter((t) -> t.getDifficulty().equals(Difficulty.EASY))
				.toList().subList(0, sortedCombination[0]));
		user.getDailyTasks().addAll(tasks.stream()
				.filter((t) -> t.getDifficulty().equals(Difficulty.MEDIUM))
				.toList().subList(0, sortedCombination[1]));
		user.getDailyTasks().addAll(tasks.stream()
				.filter((t) -> t.getDifficulty().equals(Difficulty.HARD))
				.toList().subList(0, sortedCombination[2]));
	}

	private static List<int[]> findCombinations(int easyMedia, int mediumMedia, int hardMedia, int objective, int margin) {
		List<int[]> validCombinations = new ArrayList<>();

		int maxF = objective / easyMedia + 1;
		int maxM = objective / mediumMedia + 1;
		int maxD = objective / hardMedia + 1;

		for (int f = 0; f <= maxF; f++) {
			for (int m = 0; m <= maxM; m++) {
				for (int d = 0; d <= maxD; d++) {
					int totalTime = f * easyMedia + m * mediumMedia + d * hardMedia;

					if (Math.abs(totalTime - objective) <= margin) {
						validCombinations.add(new int[]{f, m, d, totalTime});
					}
				}
			}
		}

		return validCombinations;
	}

	private static Integer mediaTasksTempo(List<Task> tasks) {
		if (tasks == null || tasks.isEmpty()) {
			return 0;
		}

		return (int) tasks.stream()
				.filter(Objects::nonNull) // Filtra tasks nulas
				.map(Task::getTimeSecs)      // Mapeia para Integer (segundos)
				.filter(Objects::nonNull) // Filtra tempos nulos
				.mapToInt(Integer::intValue) // Converte para int
				.average()                // Calcula a média
				.orElse(0.0);
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
