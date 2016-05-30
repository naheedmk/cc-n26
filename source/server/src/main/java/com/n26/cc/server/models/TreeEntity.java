package com.n26.cc.server.models;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class TreeEntity<T extends TreeEntity<T>> {
	
	private T parent;
	
	private final Set<T> children = new HashSet<>();
	
	@SuppressWarnings("unchecked")
	public void setParent(final T parent) {
		if(Objects.equals(this.parent, parent))
			return;
		if(null == this.parent)
			this.parent = parent;
		this.parent.getChildren().remove(this);
		this.parent = parent;
		if(null == parent)
			return;
		this.parent.getChildren().add((T) this);
	}
	
	public T getParent() {
		return parent;
	}
	
	public Set<T> getChildren() {
		return children;
	}
	
}
