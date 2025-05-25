package com.blooging.blog;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BlogAppsApisApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogAppsApisApplication.class, args);
	}
	
	@Bean // A normal Java object that Spring creates, manages, and gives to you when you need it.
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
