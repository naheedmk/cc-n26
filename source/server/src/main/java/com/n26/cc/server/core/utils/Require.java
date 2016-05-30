package com.n26.cc.server.core.utils;

import com.n26.cc.server.core.exceptions.WebApplicationExceptions;

import org.apache.commons.lang3.Validate;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import java.util.Optional;
import java.util.function.Supplier;

public class Require {

	public static void isTrue(final boolean expression, final Supplier<? extends WebApplicationException> orElse) {
		try {
			Validate.isTrue(expression);
		} catch (final IllegalArgumentException e) {
			throw orElse.get();
		}
	}
	
	public static void isTrue(final boolean expression, final Status status) {
		isTrue(expression, WebApplicationExceptions.using(status));
	}
	
	public static void isTrue(final boolean expression, final Status status, final String message) {
		isTrue(expression, WebApplicationExceptions.using(status, message));
	}
	
	public static void isTrue(final boolean expression, final Status status, final String message, final Object ... args) {
		isTrue(expression, WebApplicationExceptions.using(status, message, args));
	}

	private static <T> Optional<T> notNull(final T object) {
		try {
			return Optional.of(Validate.notNull(object));
		} catch (NullPointerException e) {
			return Optional.empty();
		}
	}
	
	public static <T> T notNull(final T object, final Status status) {
		return notNull(object).orElseThrow(() -> new WebApplicationException(status));
	}
	
	public static <T> T notNull(final T object, final Status status, final String message) {
		return notNull(object).orElseThrow(() -> new WebApplicationException(message, status));
	}
	
	public static <T> T notNull(final T object, final Status status, final String message, final Object ... args) {
		return notNull(object).orElseThrow(() -> new WebApplicationException(String.format(message, args), status));
	}
	
}
