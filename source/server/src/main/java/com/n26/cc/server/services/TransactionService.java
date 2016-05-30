package com.n26.cc.server.services;

import com.n26.cc.server.core.exceptions.ServiceException;
import com.n26.cc.server.models.TransactionDTO;
import com.n26.cc.server.models.TransactionSummingDTO;

import java.util.Collection;

public interface TransactionService {

	ServiceResponse<Collection<TransactionDTO>> getAll();
	
	ServiceResponse<TransactionDTO> get(Long tid);

	ServiceResponse<long[]> getByType(String type);

	ServiceResponse<TransactionSummingDTO> getSum(Long tid);

	ServiceResponse<TransactionDTO> post(Long id, TransactionDTO transaction) throws ServiceException;

	ServiceResponse<TransactionDTO> put(Long id, TransactionDTO transaction) throws ServiceException;
	
}
