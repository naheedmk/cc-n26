package com.n26.cc.server.core.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import java.util.function.Supplier;

public class WebApplicationExceptions {

	public static Supplier<? extends WebApplicationException> using(final Status status) {
		return using(status, null);
	}

	public static Supplier<? extends WebApplicationException> using(final Status status, final String message) {
		return using(status, message, new Object[]{});
	}

	public static Supplier<? extends WebApplicationException> using(final Status status, final String message, final Object ... args) {		
		return () -> new WebApplicationException(String.format(message, args), status);
	}

}
