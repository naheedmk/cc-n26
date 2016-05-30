package com.n26.cc.server.services;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class ServiceResponse<T> {
	
	protected final T entity;
	
	protected final Status status;

	public ServiceResponse(final Status status, final T entity) {
		this.entity = entity;
		this.status = status;
	}

	public Response build(){
		return Response.status(status).entity(entity).build();
	}
	
}
