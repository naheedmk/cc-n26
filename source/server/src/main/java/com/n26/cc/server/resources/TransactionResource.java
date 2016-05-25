package com.n26.cc.server.resources;

import com.n26.cc.server.core.RestValidate;
import com.n26.cc.server.core.WebApplicationExceptions;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;


@Component
@Profile("n26")
@Path("/transactionservice")
@Produces(MediaType.APPLICATION_JSON)
@ExposesResourceFor(TransactionDTO.class)
public class TransactionResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionResource.class);
	
	@Inject
	private TransactionService transactionService;

	public TransactionResource() {
		LOGGER.trace(":new");
	}
	
	/**
	 * <code>GET /transactionservice/transaction/$transaction_id</code>
	 * <p>
	 * Returns: <code>{"amount":double,"type":string,"parent_id":long}<code>
	 * 
	 * @param tid
	 * @return
	 */
	@GET
	@Path("/transaction/{tid}")
	public Response get(@PathParam("tid") final Long tid) {
		LOGGER.trace("GET transaction/{}", tid);
		return Response.ok(transactionService.get(tid)).build();
	}

	/**
	 * <code>GET /transactionservice/types/$type</code>
	 * <p>
	 * Returns: <code>[ long, long, .... ]</code> 
	 * <p>
	 * A json list of all transaction ids that share the same type $type.
	 * 
	 * @param type
	 * @return
	 */
	@GET
	@Path("/types/{type}")
	public Response getTypes(@PathParam("type") final String type) {
		LOGGER.trace("GET types/{}", type);
		return Response.ok(transactionService.getByType(type)).build();
	}

	/**
	 * <code>GET /transactionservice/sum/$transaction_id</code>
	 * <p>
	 * Returns: <code>{ "sum", double }</code>
	 * <p>
	 * A sum of all transactions that are transitively linked by their parent_id to <code>$transaction_id</code>.
	 * 
	 * @param tid
	 * @return
	 */
	@GET
	@Path("/sum/{tid}")
	public Response getSum(@PathParam("tid") final Long tid) {
		LOGGER.trace("GET sum/{}", tid);
		return Response.ok(transactionService.getSum(tid)).build();
	}

	/**
	 * <code>PUT /transactionservice/transaction/$transaction_id</code>
	 * <p>
	 * Body: <code>{"amount":double,"type":string,"parent_id":long }</code>
	 * <p>
	 * where: <code>transaction_id</code> is a long specifying a new transaction amount is a double
	 * specifying the amount type is a string specifying a type of the transaction.
	 * <code>parent_id</code> is an optional long that may specify the parent transaction of this
	 * transaction.
	 * 
	 * @param tid
	 * @param transaction
	 * @return
	 */
	@PUT
	@Path("/transaction/{tid}")
	public Response update(@PathParam("tid") final Long tid, @Valid final TransactionDTO transaction) {
		LOGGER.trace("PUT transaction/{}", tid);
		
		RestValidate.isTrue(null == transaction.getId() || tid.equals(transaction.getId()), 
			WebApplicationExceptions.using(Status.BAD_REQUEST, "Paramater `id` (%d) must match entity `id` (%d)", tid, transaction.getId()));
		
		transaction.setId(tid);
		return Response.ok(transactionService.update(transaction)).build();
	}

}
