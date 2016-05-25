package com.n26.cc.server.stores;

import com.n26.cc.server.core.Store;
import com.n26.cc.server.models.Transaction;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class TransactionStore implements Store<Long, Transaction>{

	protected final Map<Long, Transaction> store = Collections.synchronizedMap(new HashMap<>());
	
}
