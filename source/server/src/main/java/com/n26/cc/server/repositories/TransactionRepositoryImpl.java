package com.n26.cc.server.repositories;

import com.n26.cc.server.core.WebApplicationExceptions;
import com.n26.cc.server.models.Transaction;
import com.n26.cc.server.stores.TransactionStore;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.ws.rs.core.Response.Status;

import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TransactionRepositoryImpl implements TransactionRepository {

	private static final Collector<Transaction, ?, Collection<Transaction>> COLLECTOR_SAFE_ARRAY_LIST = 
		Collectors.toCollection(CopyOnWriteArrayList::new);

	@Inject
	private TransactionStore transactionStore;

	@Override
	public Optional<Transaction> get(final Long tid) {
		Objects.requireNonNull(tid, "transaction_id");
		return transactionStore.get(tid);
	}

	@Override
	public Collection<Transaction> getAll() {
		return transactionStore.getAll();
	}
	
	@Override
	public Collection<Transaction> getAll(final Long tid) throws NullPointerException {
		Objects.requireNonNull(tid, "transaction_id");
		
		/* make sure no other thread writes into store until we are ready */
		synchronized (transactionStore) {
			final Transaction child = get(tid).orElseThrow(WebApplicationExceptions.using(Status.NOT_FOUND));
			if (null == child.getParentId())
				return Stream.of(child).collect(COLLECTOR_SAFE_ARRAY_LIST);

			final Transaction parent = getAll().stream()
				.filter(t -> t.getChildren().contains(child))
				.max(Comparator.comparing(t -> t.getChildren().size()))
				.orElseThrow(WebApplicationExceptions.using(Status.INTERNAL_SERVER_ERROR, "Inconsistent data"));

			return Stream.of(parent)
				.map(Transaction::getChildren)
				.flatMap(c -> c.stream())
				.collect(COLLECTOR_SAFE_ARRAY_LIST);
		}
	}

	@Override
	public Collection<Transaction> getByType(final String type) {
		Objects.requireNonNull(type, "type");
		return transactionStore.getAll().stream()
			.filter(t -> type.equals(t.getType()))
			.collect(COLLECTOR_SAFE_ARRAY_LIST);
	}

	@Override
	public Transaction update(final Transaction entity) {
		Objects.requireNonNull(entity, "transaction");
		
		/* make sure no other thread uses store until we are ready (reentrant synchronization) */
		synchronized (transactionStore) {
			if(null == entity.getParentId())
				return transactionStore.put(entity);
			Transaction parent = get(entity.getParentId()).orElseThrow(WebApplicationExceptions.using(Status.NOT_FOUND, "Unknown parent_id"));
			parent.addChildren(entity);
			update(parent);
			return transactionStore.put(entity);
		}
	}

}
