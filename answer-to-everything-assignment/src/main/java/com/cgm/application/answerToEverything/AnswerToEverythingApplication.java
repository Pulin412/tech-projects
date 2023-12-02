package com.cgm.application.answerToEverything;

import com.cgm.service.IAnswerToEverythingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.cgm.application.*"})
public class AnswerToEverythingApplication implements CommandLineRunner {

	@Autowired
	private	IAnswerToEverythingService answerToEverythingService;

	public static void main(String[] args) {
		SpringApplication.run(AnswerToEverythingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(answerToEverythingService.evaluateInput(args));
	}
}
