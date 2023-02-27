package me.readyplayer.reportingservice;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class ReportingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReportingServiceApplication.class, args);
	}

	@PostConstruct
	public void init() {
		// Setting the TimeZone
		TimeZone.setDefault(TimeZone.getTimeZone("EET"));
	}

}
