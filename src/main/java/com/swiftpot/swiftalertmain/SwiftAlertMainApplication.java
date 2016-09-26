package com.swiftpot.swiftalertmain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class SwiftAlertMainApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(SwiftAlertMainApplication.class, args);
	}
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SwiftAlertMainApplication.class);
	}
}
