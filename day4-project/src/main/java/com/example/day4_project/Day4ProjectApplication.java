package com.example.day4_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication
public class Day4ProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(Day4ProjectApplication.class, args);
	}

}
