package com.n26.cc.server.core.exceptions;

import javax.ws.rs.core.Response;

public abstract class ServiceException extends Exception {

	private static final long serialVersionUID = 6898672085025043194L;

	public abstract Response buildResponse();
	
}
