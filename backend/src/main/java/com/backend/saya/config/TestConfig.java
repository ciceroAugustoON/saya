package com.backend.saya.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.backend.saya.services.LoginService;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner{
	@Autowired
	private LoginService loginService;

	@Override
	public void run(String... args) throws Exception {
		loginService.register("a@gmail.com", "Ana", "asdf");
		
	}
	
}
