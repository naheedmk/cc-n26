package com.n26.cc.server.services;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

import com.n26.cc.server.core.exceptions.ServiceException;
import com.n26.cc.server.core.exceptions.WebApplicationExceptions;
import com.n26.cc.server.core.utils.Require;
import com.n26.cc.server.mappers.TransactionMapper;
import com.n26.cc.server.models.Transaction;
import com.n26.cc.server.models.TransactionDTO;
import com.n26.cc.server.models.TransactionSummingDTO;
import com.n26.cc.server.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.ws.rs.core.Response.Status;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Inject
	private TransactionRepository repo;

	@Inject
	private TransactionMapper mapper;

	@Override
	public ServiceResponse<Collection<TransactionDTO>> getAll() {
		Collection<TransactionDTO> dtos = repo.getAll().stream().map(mapper::toDTO).collect(Collectors.toCollection(CopyOnWriteArrayList::new));
		return new ServiceResponse<>(Status.OK, dtos);
	}
	
	@Override
	public ServiceResponse<TransactionDTO> get(final Long id) {
		Require.notNull(id, Status.INTERNAL_SERVER_ERROR);
		
		Transaction entity = repo.get(id).orElseThrow(WebApplicationExceptions.using(Status.NOT_FOUND));
		return new ServiceResponse<>(Status.OK, mapper.toDTO(entity));
	}

	@Override
	public ServiceResponse<long[]> getByType(final String type) {
		Require.notNull(type, INTERNAL_SERVER_ERROR);

		long[] entity = repo.getByType(type).stream().mapToLong(Transaction::getId).toArray();
		return new ServiceResponse<>(Status.OK, entity);
	}
	
	protected final static ToDoubleFunction<Transaction> TRANSACTION_SUMMING = x -> {
		final Function<Transaction, Double> transactionSummingInner = t -> {
	    	return t.getAmount() + t.getChildren().stream().mapToDouble(TransactionServiceImpl.TRANSACTION_SUMMING).sum();
	    };
	    return transactionSummingInner.apply(x);
	};
		
	@Override
	public ServiceResponse<TransactionSummingDTO> getSum(final Long id) {
		Require.notNull(id, INTERNAL_SERVER_ERROR);

		Transaction transaction = repo.get(id).orElseThrow(WebApplicationExceptions.using(Status.NOT_FOUND));
		return new ServiceResponse<>(Status.OK, new TransactionSummingDTO(TRANSACTION_SUMMING.applyAsDouble(transaction)));
	}

	@Override
	public ServiceResponse<TransactionDTO> post(final Long id, final TransactionDTO dto) throws ServiceException {
		Require.notNull(id, INTERNAL_SERVER_ERROR);
		Require.notNull(dto, INTERNAL_SERVER_ERROR);

		Transaction entity = mapper.toEntity(id, dto);
		entity.setId(id);
		return new ServiceResponse<>(Status.CREATED, mapper.toDTO(repo.post(entity)));
	}

	/**
	 * Following RFC2616 this method returns ServiceResponse with<ul>
	 * <li>Status 201 (Created) - if a new resource was created</li>
	 * <li>Status 200 (OK) - if a resource was successfully adjusted</li>
	 * <li>Status 5xx - if any error occurred</li></ul>
	 */
	@Override
	public ServiceResponse<TransactionDTO> put(final Long id, final TransactionDTO transaction) throws ServiceException {
		Require.notNull(id, INTERNAL_SERVER_ERROR);
		Require.notNull(transaction, INTERNAL_SERVER_ERROR);
		
		Transaction entity = mapper.toEntity(id, transaction);
		entity.setId(id);
		if (repo.get(id).isPresent())
			return new ServiceResponse<>(Status.OK, mapper.toDTO(repo.put(entity)));
		return new ServiceResponse<>(Status.CREATED, mapper.toDTO(repo.post(entity)));
	}

}
