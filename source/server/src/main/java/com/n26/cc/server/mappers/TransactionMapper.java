package com.n26.cc.server.mappers;

import com.n26.cc.server.core.exceptions.WebApplicationExceptions;

import com.n26.cc.server.models.Transaction;
import com.n26.cc.server.models.TransactionDTO;
import com.n26.cc.server.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.ws.rs.core.Response.Status;

/**
 * I've tried some MappingFrameworks this weeks like <code>MapStruct</code>,
 * <code>ModelMapper</code> and <code>JMapper</code>, but none of them is able to handle all
 * different mapping situations I could think of. I do believe it's best to do mappings manually
 * until I was proved wrong.
 */
@Service
public class TransactionMapper implements Mapper<Long, Transaction, TransactionDTO> {

	@Inject
	private TransactionRepository repo;

	@Override
	public TransactionDTO toDTO(final Transaction from) {
		if (null == from)
			return null;
		final TransactionDTO dto = new TransactionDTO();
		dto.setAmount(from.getAmount());
		dto.setParentId(null == from.getParent() ? null : from.getParent().getId());
		dto.setType(from.getType());
		return dto;
	}

	@Override
	public Transaction toEntity(final Long id, final TransactionDTO from) {
		if (null == from)
			return null;
		final Transaction entity = repo.get(id).orElse(new Transaction());
		entity.setId(id);
		entity.setAmount(from.getAmount());
		entity.setParent(null == from.getParentId() ? null : repo.get(from.getParentId()).orElseThrow(
			WebApplicationExceptions.using(Status.BAD_REQUEST, "Parent with id=%d does not exist.", from.getParentId())));
		entity.setType(from.getType());
		return entity;
	}


}
