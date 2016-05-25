package com.n26.cc.server;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

@SpringBootApplication
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Application extends SpringBootServletInitializer {

	@Override
	public final SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}
	
	public static void main(final String[] args) {
		SpringApplication app = new SpringApplication(Application.class); 
		app.setBannerMode(Mode.OFF);
		app.setLogStartupInfo(false);
		app.run(args);
	}

}
