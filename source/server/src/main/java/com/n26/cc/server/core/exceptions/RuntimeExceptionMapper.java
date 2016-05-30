package com.n26.cc.server.core.exceptions;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;

public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException>{

	@Inject
	private UriInfo uriInfo;
	
	@Override
	public Response toResponse(final RuntimeException e) {
		Response response = Response.serverError().build();
		ErrorResponse r = new ErrorResponse();
		r.setError(response.getStatusInfo().getReasonPhrase());
		r.setMessage(e.getMessage());
		r.setPath(uriInfo.getAbsolutePath());
		r.setStatus(response.getStatus());
		r.setTimestamp(System.currentTimeMillis());
		return Response.status(response.getStatus()).entity(r).build();
	}

}
