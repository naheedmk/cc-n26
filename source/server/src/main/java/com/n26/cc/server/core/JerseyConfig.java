package com.n26.cc.server.core;

import com.n26.cc.server.TransactionResource;
import com.n26.cc.server.core.exceptions.RuntimeExceptionMapper;
import com.n26.cc.server.core.exceptions.WebApplicationExceptionMapper;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.hateoas.config.EnableEntityLinks;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

@Component
@EnableEntityLinks
@Profile("n26")
@ApplicationPath("/")
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		register(TransactionResource.class);
		register(RuntimeExceptionMapper.class);
		register(WebApplicationExceptionMapper.class);
		property(ServerProperties.BV_DISABLE_VALIDATE_ON_EXECUTABLE_OVERRIDE_CHECK, Boolean.TRUE);		
	}

}
