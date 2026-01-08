package com.venkatesh.vidhvyn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class VidhvynApplication {

	public static void main(String[] args) {

		SpringApplication.run(VidhvynApplication.class, args);
		System.out.println("Application Started....");
	}

}
