package com.n26.cc.server.repositories;

import com.n26.cc.server.core.exceptions.WebApplicationExceptions;
import com.n26.cc.server.models.Transaction;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response.Status;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class TransactionRepositoryImpl implements TransactionRepository {

	private static final Collector<Transaction, ?, Collection<Transaction>> COLLECTOR_SAFE_ARRAY_LIST = Collectors
		.toCollection(CopyOnWriteArrayList::new);

	private final ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();
	private final Lock readLock = rwlock.readLock();
	private final Lock writeLock = rwlock.writeLock();

	private final Map<Long, Transaction> transactionMap = new HashMap<>();

	@Override
	public Optional<Transaction> get(final Long id) {
		Validate.notNull(id);
		readLock.lock();
		try {
			return Optional.ofNullable(transactionMap.get(id));
		} finally {
			readLock.unlock();
		}
	}
	
	@Override
	public Collection<Transaction> getAll() {
		readLock.lock();
		try {
			return transactionMap.values();
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public Collection<Transaction> getByType(final String type) {
		Validate.notNull(type, "entity");

		readLock.lock();
		try {
			return transactionMap.values().stream()
				.filter(t -> type.equals(t.getType()))
				.collect(COLLECTOR_SAFE_ARRAY_LIST);
		} finally {
			readLock.unlock();
		}
		
	}

	@Override
	public Transaction post(final Transaction entity) {
		Validate.notNull(entity, "entity");
		writeLock.lock();
		try {
			if(transactionMap.containsKey(entity.getId()))
				throw WebApplicationExceptions.using(Status.CONFLICT, "Transaction with id=%d already exists.", entity.getId()).get();
			transactionMap.put(entity.getId(), entity);
			return transactionMap.get(entity.getId());
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public Transaction put(final Transaction entity) {
		Validate.notNull(entity, "entity");
		writeLock.lock();
		try {
			transactionMap.put(entity.getId(), entity);
			return transactionMap.get(entity.getId());
		} finally {
			writeLock.unlock();
		}
	}

}
