package com.kodilla.project;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class KodillaFinalProjectApplication extends SpringBootServletInitializer implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(KodillaFinalProjectApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(KodillaFinalProjectApplication.class);
	}

	@Override
	public void run(String... args) {
		//SCHEDULER SQL QUERRY
//		drop event count_events;
//		delimiter $$
//		create event count_events on schedule every 1 week
//		do begin
//		declare events_counter int;
//		declare message varchar(255);
//		select count(id) from events as events_counter;
//		set message = "Events count: "+events_counter;
//		insert into logs (description, type)
//		values(message, "Scheduled");
//		commit;
//		end $$
//		delimiter ;
//		select * from logs;
	}
}
