package com.n26.cc.server.models;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class Transaction extends AbstractEntity {

	private Long tid;

	private double amount;

	private String type;

	private final Set<Transaction> children = new CopyOnWriteArraySet<>();
	
	private Long parentId;

	public Long getTid() {
		return tid;
	}

	public void setTid(final Long tid) {
		this.tid = tid;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(final double amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(final Long parentId) {
		this.parentId = parentId;
	}

	public Set<Transaction> getChildren() {
		return children;
	}
	
	public boolean addChildren(final Transaction e) {
		return children.add(e);
	}
	
	public boolean removeChild(final Transaction e){
		return children.remove(e);
	}

}
