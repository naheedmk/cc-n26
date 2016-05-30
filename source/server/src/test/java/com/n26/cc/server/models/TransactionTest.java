package com.n26.cc.server.models;


public class TransactionTest {
	
	public static Transaction create(final int i, final Transaction parent) {
		return create(Long.valueOf(i), parent, null == parent ? "type" : parent.getType(), Math.random()*1000);
	}

	public static Transaction create(final Long id, final Transaction parent, final String type, final Double amount){
		Transaction t = new Transaction();
		t.setId(id);
		t.setParent(parent);
		t.setType(type);
		t.setAmount(amount);
		return t;
	}
	
}
