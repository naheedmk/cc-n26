package com.n26.cc.server.mappers;

import com.n26.cc.server.models.Transaction;
import com.n26.cc.server.models.TransactionDTO;
import org.springframework.stereotype.Service;

/**
 * I've tried some MappingFrameworks this weeks like <code>MapStruct</code>,
 * <code>ModelMapper</code> and <code>JMapper</code>, but none of them is able to handle all
 * different mapping situations I could think of. I do believe it's best to do mappings manually
 * until I was proved wrong.
 */
@Service
public class TransactionMapper implements Mapper<Transaction, TransactionDTO> {

	@Override
	public TransactionDTO toDTO(final Transaction from) {
		if(null == from)
			return null;
		final TransactionDTO dto = new TransactionDTO();
		dto.setAmount(from.getAmount());
		dto.setId(from.getTid());
		dto.setParentId(from.getParentId());
		dto.setType(from.getType());
		return dto;
	}

	@Override
	public Transaction toEntity(final TransactionDTO from) {
		if(null == from)
			return null;
		final Transaction entity = new Transaction();
		entity.setAmount(from.getAmount());
		entity.setParentId(from.getParentId());
		entity.setTid(from.getId());
		entity.setType(from.getType());
		return entity;
	}


}
