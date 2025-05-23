package com.backend.saya.config;

import java.util.List;

import com.backend.saya.dto.UserRequest;
import com.backend.saya.entities.enumeration.Segmentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.backend.saya.entities.Habit;
import com.backend.saya.entities.Task;
import com.backend.saya.entities.enumeration.Difficulty;
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
		Habit habit = new Habit(null, "Geral", Segmentation.HEALTH_CARE);
		Habit habit2 = new Habit(null, "Ciclismo", Segmentation.PHYSICAL_ACTIVITY);
		Habit habit3 = new Habit(null, "Meditação", Segmentation.MEDITATION);
		Habit habit4 = new Habit(null, "Leitura", Segmentation.INTELLECTUAL_ACTIVITY);
		Habit habit5 = new Habit(null, "Caminhada", Segmentation.PHYSICAL_ACTIVITY);
		Habit habit6 = new Habit(null, "Xadrez", Segmentation.LEISURE);
		Habit habit7 = new Habit(null, "Alongamento", Segmentation.PHYSICAL_ACTIVITY);
		Habit habit8 = new Habit(null, "Idioma", Segmentation.INTELLECTUAL_ACTIVITY);
		habitRepository.saveAllAndFlush(List.of(habit, habit2, habit3, habit4, habit5, habit6, habit7, habit8));

		Task task01 = new Task(null, "Beba um copo de água", "Ingira pelo menos 250ml de água", Difficulty.EASY, 60);
		task01.setHabit(habit);
		Task task02 = new Task(null, "Faça uma caminhada de 10 minutos", "Caminhe por 10 minutos ao ar livre ou em esteira", Difficulty.EASY, 600);
		task02.setHabit(habit);
		Task task03 = new Task(null, "Anote 3 coisas boas do dia", "Reflita e registre momentos positivos", Difficulty.MEDIUM, 300);
		task03.setHabit(habit);
		
		Task task04 = new Task(null, "Ande de bicicleta por 10 minutos", "De uma volta no seu bairro ou local de preferência", Difficulty.MEDIUM, 600);
		task04.setHabit(habit2);
		Task task05 = new Task(null, "Pedale 5km", "Escolha um percurso de 5km e complete o trajeto de bicicleta", Difficulty.MEDIUM, 2400);
		task05.setHabit(habit2);
		Task task06 = new Task(null, "Faça um sprint de 1 minuto", "Pedale o mais rápido que conseguir durante 1 minuto", Difficulty.HARD, 600);
		task06.setHabit(habit2);
		
		Task task07 = new Task(null, "Medite por 5 minutos", "Encontre um local tranquilo e foque na sua respiração por 5 minutos", Difficulty.EASY, 300);
		task07.setHabit(habit3);
		Task task08 = new Task(null, "Faça uma meditação guiada", "Escolha uma meditação guiada de 10 minutos em um aplicativo ou vídeo", Difficulty.MEDIUM, 600);
		task08.setHabit(habit3);
		Task task09 = new Task(null, "Pratique a meditação da atenção plena", "Durante o dia, reserve 3 minutos para observar seus pensamentos e sensações sem julgá-los", Difficulty.MEDIUM, 180);
		task09.setHabit(habit3);

		Task task10 = new Task(null, "Leia 10 páginas", "Escolha um livro preferencialmente físico ou em um e-reader", Difficulty.EASY, 600);
		task10.setHabit(habit4);
		Task task11 = new Task(null, "Resuma um capítulo lido", "Escreva 3 pontos principais", Difficulty.MEDIUM, 900);
		task11.setHabit(habit4);
		Task task12 = new Task(null, "Pesquise sobre um autor", "Leia uma biografia ou entrevista", Difficulty.HARD, 1200);
		task12.setHabit(habit4);

		Task task13 = new Task(null, "Caminhe 5 minutos", "Ande em ritmo confortável", Difficulty.EASY, 300);
		task13.setHabit(habit5);
		Task task14 = new Task(null, "Caminhada com subida", "Inclua ladeiras ou escadas", Difficulty.MEDIUM, 900);
		task14.setHabit(habit5);
		Task task15 = new Task(null, "Caminhe 5km", "Use um app para medir a distância", Difficulty.HARD, 1800);
		task15.setHabit(habit5);

		Task task16 = new Task(null, "Jogue uma partida rápida", "Online ou com um amigo", Difficulty.EASY, 600);
		task16.setHabit(habit6);
		Task task17 = new Task(null, "Estude uma abertura", "Pratique a abertura italiana", Difficulty.MEDIUM, 1200);
		task17.setHabit(habit6);
		Task task18 = new Task(null, "Analise uma partida clássica", "Assista a uma análise de Kasparov", Difficulty.HARD, 2400);
		task18.setHabit(habit6);

		Task task19 = new Task(null, "Alongue pernas por 3 minutos", "Foque em panturrilhas e quadríceps", Difficulty.EASY, 180);
		task19.setHabit(habit7);
		Task task20 = new Task(null, "Sessão de alongamento completo", "Cubra braços, pernas e coluna", Difficulty.MEDIUM, 600);
		task20.setHabit(habit7);
		Task task21 = new Task(null, "Yoga para flexibilidade", "Siga uma rotina de 15 minutos", Difficulty.HARD, 900);
		task21.setHabit(habit7);

		Task task22 = new Task(null, "Aprenda 5 palavras novas", "Use flashcards ou app", Difficulty.EASY, 300);
		task22.setHabit(habit8);
		Task task23 = new Task(null, "Ouça um podcast no idioma", "Anote 3 frases que entendeu", Difficulty.MEDIUM, 900);
		task23.setHabit(habit8);
		Task task24 = new Task(null, "Converse por 10 minutos", "Use plataformas como Tandem ou HelloTalk", Difficulty.HARD, 1800);
		task24.setHabit(habit8);

		taskRepository.saveAll(List.of(task01, task02, task03, task04, task05,
				task06, task07, task08, task09, task10, task11, task12, task13,
				task14, task15, task16, task17, task18, task19, task20, task21,
				task22, task23, task24));
	}
	
}
