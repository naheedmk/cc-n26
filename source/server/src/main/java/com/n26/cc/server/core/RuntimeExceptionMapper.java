package com.n26.cc.server.core;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException>{

	@Override
	public Response toResponse(final RuntimeException exception) {
		// TODO Auto-generated method stub
		return null;
	}

}
