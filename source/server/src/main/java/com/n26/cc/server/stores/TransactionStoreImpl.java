package com.n26.cc.server.stores;

import com.n26.cc.server.models.Transaction;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class TransactionStoreImpl extends TransactionStore {
	
	@Override
	public Optional<Transaction> get(final Long id) {
		return Optional.ofNullable(store.get(id));
	}

	@Override
	public Collection<Transaction> getAll() {
		return store.values();
	}

	@Override
	public Transaction put(final Transaction entity) {
		store.put(entity.getTid(), entity);
		return entity;
	}
	
}
