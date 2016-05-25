package com.n26.cc.server.core;

import java.util.ArrayList;
import java.util.List;

public class Tree<T> {

	private final Node<T> root;

	public Tree(final T rootData) {
		root = new Node<>();
		root.setData(rootData);
		root.setChildren(new ArrayList<>());
	}

	public static final class Node<T> {

		private T data;

		private Node<T> parent;

		private List<Node<T>> children;

		public T getData() {
			return data;
		}

		public void setData(final T data) {
			this.data = data;
		}

		public Node<T> getParent() {
			return parent;
		}

		public void setParent(final Node<T> parent) {
			this.parent = parent;
		}

		public List<Node<T>> getChildren() {
			return children;
		}

		public void setChildren(final List<Node<T>> children) {
			this.children = children;
		}

	}

}
