package com.n26.cc.server.services;

import com.n26.cc.server.core.WebApplicationExceptions;
import com.n26.cc.server.mappers.TransactionMapper;
import com.n26.cc.server.models.Transaction;
import com.n26.cc.server.models.TransactionDTO;
import com.n26.cc.server.models.TransactionSummingDTO;
import com.n26.cc.server.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.ws.rs.core.Response.Status;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Inject
	private TransactionRepository transactionRepository;
	
	@Inject
	private TransactionMapper transactionMapper;
	
	@Override
	public TransactionDTO get(final Long tid) {
		return transactionMapper.toDTO(transactionRepository.get(tid).orElseThrow(WebApplicationExceptions.using(Status.NOT_FOUND)));
	}

	@Override
	public long[] getByType(final String type) {
		return transactionRepository.getByType(type).stream().mapToLong(Transaction::getTid).toArray();
	}

	@Override
	public TransactionSummingDTO getSum(final Long tid) {
		double sum = transactionRepository.getAll(tid).stream().mapToDouble(Transaction::getAmount).sum();
		return new TransactionSummingDTO(sum);
	}

	@Override
	public TransactionDTO update(final TransactionDTO transaction) {
		return transactionMapper.toDTO(transactionRepository.update(transactionMapper.toEntity(transaction)));
	}
	
}
