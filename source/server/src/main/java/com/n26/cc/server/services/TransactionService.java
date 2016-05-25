package com.n26.cc.server.services;

import com.n26.cc.server.models.TransactionDTO;
import com.n26.cc.server.models.TransactionSummingDTO;

public interface TransactionService {

	TransactionDTO get(Long tid);

	long[] getByType(String type);

	TransactionSummingDTO getSum(Long tid);

	TransactionDTO update(TransactionDTO transaction);

}
