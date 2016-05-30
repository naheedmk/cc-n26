package com.n26.cc.server.repositories;

import com.n26.cc.server.models.Transaction;
import com.n26.cc.server.models.TransactionTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.WebApplicationException;

public class TransactionRepositoryTest extends Assert {

	private TransactionRepositoryImpl repo;

	@Before
	public void before(){
		this.repo = new TransactionRepositoryImpl();
	}
	
	@Test
	public void testPut(){
		assertNotNull(repo.put(TransactionTest.create(1, null)));
		assertNotNull(repo.get(1L));
	}
	
	@Test
	public void testPutMultiple(){
		assertNotNull(repo.put(TransactionTest.create(1, null)));
		assertNotNull(repo.put(TransactionTest.create(1, null)));
	}

	@Test
	public void testPost() {
		assertNotNull(repo.post(TransactionTest.create(1, null)));
	}
	
	@Test(expected = WebApplicationException.class)
	public void testPostExisting() {
		testPut();
		repo.post(TransactionTest.create(1, null));
	}
	
	@Test
	public void testPostInLine() {
		
		Transaction t1 = TransactionTest.create(1, null);
		assertNotNull(repo.post(t1));
		
		Transaction t2 = TransactionTest.create(2, t1);
		assertNotNull(repo.post(t2));
		
		Transaction t3 = TransactionTest.create(3, t2);
		assertNotNull(repo.post(t3));
		
		assertNotNull(repo.get(1L).get());
		assertNotNull(repo.get(2L).get());
		assertNotNull(repo.get(3L).get());
		
		assertEquals(t1, repo.get(1L).get());
		assertEquals(t2, repo.get(2L).get());
		assertEquals(t3, repo.get(3L).get());
				
		assertEquals(t1, repo.get(2L).get().getParent());
		assertEquals(t2, repo.get(3L).get().getParent());
		
		assertEquals(1, repo.get(1L).get().getChildren().size());
		assertEquals(1, repo.get(2L).get().getChildren().size());
		assertEquals(0, repo.get(3L).get().getChildren().size());

	}
	
}
