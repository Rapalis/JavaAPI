package com.domrap.VismaLib;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("Book")
public class VismaLibApplication {

	public static void main(String[] args) {
		SpringApplication.run(VismaLibApplication.class, args);
	}

}
