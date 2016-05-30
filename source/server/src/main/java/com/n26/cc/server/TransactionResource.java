package com.n26.cc.server;

import com.n26.cc.server.core.exceptions.ServiceException;
import com.n26.cc.server.models.TransactionDTO;
import com.n26.cc.server.services.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Profile("n26")
@Path("transactions")
@Produces(MediaType.APPLICATION_JSON)
@ExposesResourceFor(TransactionDTO.class)
public class TransactionResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionResource.class);
	
	@Inject
	private TransactionService transactionService;
	
	@GET
	@Path("{id}")
	public Response get(@PathParam("id") final Long id) {
		LOGGER.trace("GET transaction/{}", id);
		return transactionService.get(id).build();
	}
	
	@GET
	public Response getAll(@QueryParam("type") final String type) {
		LOGGER.trace("GET transaction");
		if(null == type)	
			return transactionService.getAll().build();
		return transactionService.getByType(type).build();
	}

	@GET
	@Path("sum/{tid}")
	public Response getSum(@PathParam("tid") final Long tid) {
		LOGGER.trace("GET sum/{}", tid);
		return transactionService.getSum(tid).build();
	}

	@PUT
	@Path("{id}")
	public Response update(@PathParam("id") final Long id, @Valid final TransactionDTO transaction) {
		LOGGER.trace("PUT transaction/{}", id);
		try {
			return transactionService.put(id, transaction).build();
		} catch (ServiceException e) {
			return e.buildResponse();
		}
	}

}
