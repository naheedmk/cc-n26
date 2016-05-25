package com.n26.cc.server.core;

import java.util.Objects;

public interface Immutable<T> {

	T getValue();
	
	default void setValue(final T value){
		Objects.requireNonNull(value, "value");
		set(value);
	}
	
	void set(T value);
	
}
