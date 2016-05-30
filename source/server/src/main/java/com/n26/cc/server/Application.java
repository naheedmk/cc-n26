package com.n26.cc.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

	@Override
	public final SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}
	
	public static void main(final String[] args) {
		new Application()
			.configure(new SpringApplicationBuilder(Application.class))
			.run(args);
	}

}
