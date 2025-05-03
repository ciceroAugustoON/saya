package com.backend.saya;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SayaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SayaApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("**") // URL exata do seu frontend
						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
						.allowedHeaders("*")
						.allowCredentials(true) // Isso é crucial para credenciais
						.maxAge(3600); // Tempo de cache das opções CORS
			}
		};
	}
}
