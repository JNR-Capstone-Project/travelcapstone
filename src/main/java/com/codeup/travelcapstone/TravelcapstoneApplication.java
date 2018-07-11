package com.codeup.travelcapstone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TravelcapstoneApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelcapstoneApplication.class, args);
	}
}
