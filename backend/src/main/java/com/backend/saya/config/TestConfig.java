package com.backend.saya.config;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.backend.saya.controllers.TaskController;
import com.backend.saya.entities.Task;
import com.backend.saya.entities.User;
import com.backend.saya.entities.enumeration.Difficulty;
import com.backend.saya.repositories.UserRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner{
	@Autowired
	private UserRepository userRepository;

	@Override
	public void run(String... args) throws Exception {
		User user = new User(1L, "Cicero", "1234");
		userRepository.save(user);
	}
	
}
