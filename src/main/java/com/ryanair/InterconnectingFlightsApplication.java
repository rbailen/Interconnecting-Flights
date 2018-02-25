package com.ryanair;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.ryanair.config.Config;
import com.ryanair.config.imp.ConfigImpl;

@SpringBootApplication
public class InterconnectingFlightsApplication extends SpringBootServletInitializer{
	
	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder builder) {
		return builder.sources(InterconnectingFlightsApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(InterconnectingFlightsApplication.class, args);
	}
	
	@Bean
	public Config config(){
		return new ConfigImpl();
	}
}

