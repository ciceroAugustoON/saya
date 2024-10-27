package com.backend.saya.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.backend.saya.entities.Week;
import com.backend.saya.entities.enumeration.WeekDay;
import com.backend.saya.repositories.HabitRepository;
import com.backend.saya.repositories.TaskRepository;
import com.backend.saya.services.LoginService;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner{
	@Autowired
	private LoginService loginService;
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private HabitRepository habitRepository;

	@Override
	public void run(String... args) throws Exception {
		/*Task task01 = new Task(null, "Beba um copo de água", "Ingira pelo menos 250ml de água", Difficulty.EASY);
		Task task02 = new Task(null, "Faça uma caminhada de 10 minutos", "Caminhe por 10 minutos ao ar livre ou em um espaço fechado", Difficulty.EASY);
		Task task03 = new Task(null, "Leia 10 páginas de um livro", "Escolha um livro de sua preferência e leia pelo menos 10 páginas", Difficulty.MEDIUM);

		Habit habit = new Habit(null, "Ciclismo");
		Habit habit2 = new Habit(null, "Meditação");
		habitRepository.saveAllAndFlush(List.of(habit, habit2));
		
		Task task04 = new Task(null, "Ande de bicicleta por 10 minutos", "De uma volta no seu bairro ou local de preferência", Difficulty.MEDIUM);
		task04.setHabit(habit);
		Task task05 = new Task(null, "Pedale 5km", "Escolha um percurso de 5km e complete o trajeto de bicicleta", Difficulty.MEDIUM);
		task05.setHabit(habit);
		Task task06 = new Task(null, "Faça um sprint de 1 minuto", "Pedale o mais rápido que conseguir durante 1 minuto", Difficulty.HARD);
		task06.setHabit(habit);
		
		
		Task task07 = new Task(null, "Medite por 5 minutos", "Encontre um local tranquilo e foque na sua respiração por 5 minutos", Difficulty.EASY);
		task07.setHabit(habit2);
		Task task08 = new Task(null, "Faça uma meditação guiada", "Escolha uma meditação guiada de 10 minutos em um aplicativo ou vídeo", Difficulty.MEDIUM);
		task08.setHabit(habit2);
		Task task09 = new Task(null, "Pratique a meditação da atenção plena", "Durante o dia, reserve 3 minutos para observar seus pensamentos e sensações sem julgá-los", Difficulty.MEDIUM);
		task09.setHabit(habit2);
		taskRepository.saveAll(List.of(task01, task02, task03, task04, task05, task06, task07, task08, task09));
		
		Objectives objectives = new Objectives(10, 2);
		objectives.addHabit(habit);
		loginService.register("a@gmail.com", "Ana", "asdf");*/
		
		Week week = new Week();
		week.setEasyTasksPerDay(WeekDay.ONE, 3);
		week.setMediumTasksPerDay(WeekDay.FOUR, 3);
		week.setHardTasksPerDay(WeekDay.SEVEN, 1);
		System.out.println(week.getValuePoints());
	}
	
}
