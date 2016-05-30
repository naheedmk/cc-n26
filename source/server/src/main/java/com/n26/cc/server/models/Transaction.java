package com.n26.cc.server.models;

public class Transaction extends TreeEntity<Transaction> {

	private Long id;

	private double amount;

	private String type;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "Transaction [id=" + getId() + ", amount=" + amount + ", type=" + type + ", parent=" + getParent() + "]";
	}

}
