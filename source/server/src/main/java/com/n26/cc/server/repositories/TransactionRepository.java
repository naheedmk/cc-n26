package com.n26.cc.server.repositories;

import com.n26.cc.server.models.Transaction;

import javax.ws.rs.WebApplicationException;

import java.util.Collection;
import java.util.Optional;

public interface TransactionRepository {


	Optional<Transaction> get(Long id);

	/**
	 * @return
	 */
	Collection<Transaction> getAll();
		
	/**
	 * @param type
	 * @return
	 * @throws NullPointerException if given parameter was <code>null</code>
	 */
	Collection<Transaction> getByType(String type);

	/**
	 * @param entity
	 * @return
	 * @throws NullPointerException if given parameter was <code>null</code>
	 * @throws WebApplicationException with status Conflict if resource already exists
	 */
	Transaction post(Transaction entity);

	/**
	 * @param entity
	 * @return
	 * @throws NullPointerException if given parameter was <code>null</code>
	 */
	Transaction put(Transaction entity);
	
}
