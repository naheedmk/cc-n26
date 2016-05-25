package com.n26.cc.server.core;

import com.n26.cc.server.models.AbstractEntity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

public interface Store<ID extends Serializable, T extends AbstractEntity> {
	
	Optional<T> get(ID id);
	
	Collection<T> getAll();
	
	T put(T entity);
	
}
