package com.codingdojo.gymbuddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.codingdojo.gymbuddy.properties.FileStorageProperties;


@SpringBootApplication
@EnableConfigurationProperties({
	FileStorageProperties.class
	})
public class GymbuddyApplication {


	public static void main(String[] args) {
		SpringApplication.run(GymbuddyApplication.class, args);
	}

}
