package com.example.coreweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example"})
public class CoreWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreWebApplication.class, args);
	}

}
