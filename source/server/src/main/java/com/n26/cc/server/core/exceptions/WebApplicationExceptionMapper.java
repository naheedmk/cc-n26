package com.n26.cc.server.core.exceptions;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;

public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException>{

	@Inject
	private UriInfo uriInfo;
	
	@Override
	public Response toResponse(final WebApplicationException e) {
		Response response = e.getResponse();
		ErrorResponse r = new ErrorResponse();
		r.setError(response.getStatusInfo().getReasonPhrase());
		r.setMessage(e.getMessage());
		r.setPath(uriInfo.getAbsolutePath());
		r.setStatus(response.getStatus());
		r.setTimestamp(System.currentTimeMillis());
		return Response.status(response.getStatus()).entity(r).build();
	}

}
