package com.n26.cc.server;

import static org.assertj.core.api.Assertions.assertThat;

import com.n26.cc.server.models.TransactionDTO;
import com.n26.cc.server.models.TransactionSummingDTO;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * <b>Note:</b> For readability reasons, the test methods are <b>not</b> conform to java style regulations!<p>
 * Test execution is fixed to use an ascending name order<p>
 * Example: 
 * <pre>
 * <code>@Test</code>
 * <code>public void stage[major][minor]_[using]__[legend](){</code>
 * <code>  // test</code>
 * <code>}</code>
 * </pre>
 * 
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TransactionResourceTest {

	@LocalServerPort
	private int port;

	private static final String PATH = "http://localhost:{port}";

	private static final String RESOURCE_GET_TRANSACTIONS = PATH + "/transactions";

	private static final String RESOURCE_GET_TRANSACTION = 	PATH + "/transactions/{id}";

	private static final String RESOURCE_PUT_TRANSACTION = 	PATH + "/transactions/{id}";

	private static final String RESOURCE_GET_TYPES = 		PATH + "/transactions?type={type}";

	private static final String RESOURCE_GET_SUM = 			PATH + "/transactions/sum/{id}";

	private static final Map<Long, HttpEntity<TransactionDTO>> entityMap = new HashMap<>();

	private final TestRestTemplate restTemplate = new TestRestTemplate();


	@BeforeClass
	public static void before() {
		entityMap.put(1L, createTransaction(1_000d, "type", null));
		entityMap.put(2L, createTransaction(2_000d, "type", 1L));
		entityMap.put(3L, createTransaction(3_000d, "type", 2L));
	}

	@Test
	public void stage00_get_all__empty_repo() {
		ResponseEntity<TransactionDTO[]> entity = getAll();
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody().length).isEqualTo(0);
	}

	@Test
	public void stage11_put_one__as_insert() {
		ResponseEntity<Object> response = putOne(1L);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}
		
	@Test
	public void stage12_get_one() {
		ResponseEntity<TransactionDTO> response = getOne(1L);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualTo(entityMap.get(1L).getBody());
	}
	
	@Test
	public void stage13_get_sum() {
		ResponseEntity<TransactionSummingDTO> response = getSum(1L);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getSum()).isEqualTo(entityMap.get(1L).getBody().getAmount());
	}

	@Test
	public void stage14_get_typed() {
		ResponseEntity<long[]> entity = getByType(entityMap.get(1L).getBody().getType());
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody().length).isEqualTo(1);
		assertThat(entity.getBody()[0]).isEqualTo(1L);
	}

	@Test
	public void stage15_get_all() {
		ResponseEntity<TransactionDTO[]> entity = getAll();
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody().length).isEqualTo(1);
		assertThat(entity.getBody()[0]).isEqualTo(entityMap.get(1L).getBody());
	}

	/**
	 * 1 - 2 - 3
	 */
	@Test
	public void stage21_put_two__as_insert() {
		ResponseEntity<Object> r1 = putOne(2L);
		assertThat(r1.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		ResponseEntity<Object> r2 = putOne(3L);
		assertThat(r2.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}
	
	@Test
	public void stage22_get_one() {
		ResponseEntity<TransactionDTO> r2 = getOne(2L);
		assertThat(r2.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(r2.getBody()).isEqualTo(entityMap.get(2L).getBody());
		
		ResponseEntity<TransactionDTO> r3 = getOne(3L);
		assertThat(r3.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(r3.getBody()).isEqualTo(entityMap.get(3L).getBody());
	}
	
	@Test
	public void stage23_get_sum() {
		ResponseEntity<TransactionSummingDTO> r1 = getSum(1L);
		assertThat(r1.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(r1.getBody().getSum()).isEqualTo(6_000d);
		
		ResponseEntity<TransactionSummingDTO> r2 = getSum(2L);
		assertThat(r2.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(r2.getBody().getSum()).isEqualTo(5_000d);
		
		ResponseEntity<TransactionSummingDTO> r3 = getSum(3L);
		assertThat(r3.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(r3.getBody().getSum()).isEqualTo(3_000d);
	}

	@Test
	public void stage24_get_typed() {
		ResponseEntity<long[]> entity = getByType(entityMap.get(1L).getBody().getType());
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody().length).isEqualTo(3);
		assertThat(entity.getBody()[0]).isEqualTo(1L);
		assertThat(entity.getBody()[1]).isEqualTo(2L);
		assertThat(entity.getBody()[2]).isEqualTo(3L);
	}
	
	@Test
	public void stage25_get_all() {
		ResponseEntity<TransactionDTO[]> entity = getAll();
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody().length).isEqualTo(3);
		assertThat(entity.getBody()[0]).isEqualTo(entityMap.get(1L).getBody());
		assertThat(entity.getBody()[1]).isEqualTo(entityMap.get(2L).getBody());
		assertThat(entity.getBody()[2]).isEqualTo(entityMap.get(3L).getBody());
	}

	/**
	 * 1 - 2<br>
	 * 3
	 */
	@Test
	public void stage31_put_one__as_update() {
		entityMap.get(3L).getBody().setParentId(null);
		ResponseEntity<Object> r1 = putOne(3L);
		assertThat(r1.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	public void stage33_get_sum() {
		ResponseEntity<TransactionSummingDTO> r1 = getSum(1L);
		assertThat(r1.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(r1.getBody().getSum()).isEqualTo(3_000d);
		
		ResponseEntity<TransactionSummingDTO> r2 = getSum(2L);
		assertThat(r2.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(r2.getBody().getSum()).isEqualTo(2_000d);
		
		ResponseEntity<TransactionSummingDTO> r3 = getSum(3L);
		assertThat(r3.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(r3.getBody().getSum()).isEqualTo(3_000d);
	}
	
	@Test
	public void stage34_get_typed() {
		ResponseEntity<long[]> entity = getByType(entityMap.get(1L).getBody().getType());
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody().length).isEqualTo(3);
		assertThat(entity.getBody()[0]).isEqualTo(1L);
		assertThat(entity.getBody()[1]).isEqualTo(2L);
		assertThat(entity.getBody()[2]).isEqualTo(3L);
	}

	@Test
	public void stage35_get_all() {
		ResponseEntity<TransactionDTO[]> entity = getAll();
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody().length).isEqualTo(3);
		assertThat(entity.getBody()[0]).isEqualTo(entityMap.get(1L).getBody());
		assertThat(entity.getBody()[1]).isEqualTo(entityMap.get(2L).getBody());
		assertThat(entity.getBody()[2]).isEqualTo(entityMap.get(3L).getBody());
	}


	@Test
	public void stage41_put_one__none_existing_parent() {
		ResponseEntity<Object> response = putOne(999L, createTransaction(999d, "type", 999L));
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	
	private ResponseEntity<Object> putOne(final Long id) {
		return putOne(id, entityMap.get(id));
	}
	
	private ResponseEntity<Object> putOne(final Long id, final HttpEntity<TransactionDTO> transaction) {
		return restTemplate.exchange(RESOURCE_PUT_TRANSACTION, HttpMethod.PUT, transaction, Object.class, port, id);
	}

	private ResponseEntity<TransactionDTO> getOne(final Long id) {
		return restTemplate.getForEntity(RESOURCE_GET_TRANSACTION, TransactionDTO.class, port, id);
	}
	
	private ResponseEntity<TransactionSummingDTO> getSum(final Long id) {
		return restTemplate.getForEntity(RESOURCE_GET_SUM, TransactionSummingDTO.class, port, id);
	}

	private ResponseEntity<long[]> getByType(final String type) {
		return restTemplate.getForEntity(RESOURCE_GET_TYPES, long[].class, port, type);
	}
	
	private ResponseEntity<TransactionDTO[]> getAll() {
		return restTemplate.getForEntity(RESOURCE_GET_TRANSACTIONS, TransactionDTO[].class, port);
	}

	private static HttpEntity<TransactionDTO> createTransaction(final Double amount, final String type,
		final Long parent) {
		TransactionDTO dto = new TransactionDTO();
		dto.setAmount(amount);
		dto.setParentId(parent);
		dto.setType(type);
		return new HttpEntity<>(dto);
	}
	
}
