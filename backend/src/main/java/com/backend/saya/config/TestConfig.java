package com.backend.saya.config;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.backend.saya.controllers.TaskController;
import com.backend.saya.entities.Task;
import com.backend.saya.entities.enumeration.Difficulty;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner{
	@Autowired
	private TaskController taskController;

	@Override
	public void run(String... args) throws Exception {
		Task task = new Task(null, "Passear com o cachorro", "Ir até a praça da matriz", Difficulty.EASY, new Date(new java.util.Date().getTime()));
		Task task1 = new Task(null, "Comprar mantimentos", "Ir ao supermercado e comprar frutas e legumes", Difficulty.MEDIUM, new Date(new java.util.Date().getTime()));
		Task task2 = new Task(null, "Estudar programação", "Revisar exercícios de Java", Difficulty.HARD, new Date(new java.util.Date().getTime()));
		Task task3 = new Task(null, "Ler um livro", "Ler pelo menos 30 páginas de um livro de ficção", Difficulty.EASY, new Date(new java.util.Date().getTime()));

		taskController.insertAll(List.of(task, task1, task2, task3));
	}
	
}
