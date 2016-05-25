package com.n26.cc.server.core;

import org.apache.commons.lang3.Validate;

import javax.ws.rs.WebApplicationException;

import java.util.function.Supplier;

public class RestValidate {

	public static void isTrue(final boolean expression, final Supplier<? extends WebApplicationException> orElse) {
		try {
			Validate.isTrue(expression);
		} catch (final IllegalArgumentException e) {
			throw orElse.get();
		}
	}

}
