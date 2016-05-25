package com.n26.cc.server.repositories;

import com.n26.cc.server.models.Transaction;

import java.util.Collection;
import java.util.Optional;

public interface TransactionRepository {

	/**
	 * @param tid the <code>transaction_id</code> to look for
	 * @return 
	 * @throws NullPointerException if given parameter was <code>null</code>
	 */
	Optional<Transaction> get(Long tid) throws NullPointerException;

	Collection<Transaction> getAll();
	
	Collection<Transaction> getAll(Long tid) throws NullPointerException;
	
	Collection<Transaction> getByType(String type) throws NullPointerException;

	Transaction update(Transaction entity);

	

}
