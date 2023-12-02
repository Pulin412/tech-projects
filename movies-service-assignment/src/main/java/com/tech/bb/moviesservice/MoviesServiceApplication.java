package com.tech.bb.moviesservice;

import com.tech.bb.moviesservice.config.DataInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MoviesServiceApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(MoviesServiceApplication.class, args);
		context.getBean(DataInitializer.class).initializeData();
	}

}
