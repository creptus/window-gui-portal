package tests;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;


import models.Client;
import repository.ClientRepository;
import repository.exception.JsonConvertException;
import repository.exception.NoFindHostException;
import repository.exception.ServerValidationException;

public class TestClientRepository {
	@Test
	public void testGetClients() {
		FakeUtlabHTTPClient client = FakeUtlabHTTPClient.getInstanse();
		ClientRepository clientRep = new ClientRepository(client);
		try {
			LinkedList<Client> c = clientRep.getClients();
			assertTrue("Array count 0", c.size() != 0);

		} catch (JsonConvertException e) {
			fail();

		} catch (NoFindHostException e) {
			assertTrue(true);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}

		try {
			client.fileName = "AUTH_NO.txt";
			LinkedList<Client> c_auth = clientRep.getClients();
			assertTrue("Array count 0", c_auth.size() == 0);
		} catch (JsonConvertException e) {
			fail();

		} catch (NoFindHostException e) {
			assertTrue(true);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
	}

	@Test
	public void testGetClient() {
		FakeUtlabHTTPClient client = FakeUtlabHTTPClient.getInstanse();
		ClientRepository clientRep = new ClientRepository(client);
		try {
			Client c = clientRep.getClient(1);

			assertTrue(c.id == 1);
			assertTrue(c.seller_id == 21);
			assertTrue(c.seller.id == 21);

		} catch (JsonConvertException e) {
			fail();

		} catch (NoFindHostException e) {
			assertTrue(true);

		} catch (ServerValidationException e) {
			fail();
		}
		
		try {
			client.fileName = "AUTH_NO.txt";
			//Client c = clientRep.getClient(1);
			clientRep.getClient(1);

			fail();

		} catch (JsonConvertException e) {
			fail();

		} catch (NoFindHostException e) {
			assertTrue(true);

		} catch (ServerValidationException e) {
			//System.out.println(e.getMessage());
			assertTrue(e.getMessage().equals("AUTH_NO"));
		}
		
		try {
			client.fileName = "NO_NEED_PARAMS.txt";
			//Client c = clientRep.getClient(1);
			clientRep.getClient(1);

			fail();

		} catch (JsonConvertException e) {
			fail();

		} catch (NoFindHostException e) {
			assertTrue(true);

		} catch (ServerValidationException e) {
			//System.out.println(e.getMessage());
			assertTrue(e.getMessage().equals("NO_NEED_PARAMS"));
		}

	}
	
	
	
	@Test
	public void testSaveClient() {
		FakeUtlabHTTPClient client = FakeUtlabHTTPClient.getInstanse();
		ClientRepository clientRep = new ClientRepository(client);
		
		try {
			client.fileName = "ok_new.txt";
			Client c=new Client();
			
			clientRep.saveClient(c);

			assertTrue(true);
			

		} catch (JsonConvertException e) {
			fail();

		} catch (NoFindHostException e) {
			assertTrue(true);

		} catch (ServerValidationException e) {
			fail();
		}
		
		try {
			client.fileName = "ok_edit.txt";
			Client c=new Client();
			
			clientRep.saveClient(c);

			assertTrue(true);
			

		} catch (JsonConvertException e) {
			fail();

		} catch (NoFindHostException e) {
			assertTrue(true);

		} catch (ServerValidationException e) {
			fail();
		}
		
		
		try {
			client.fileName = "AUTH_NO.txt";
			Client c=new Client();
			
			clientRep.saveClient(c);

			assertTrue(true);
			

		} catch (JsonConvertException e) {
			fail();

		} catch (NoFindHostException e) {
			assertTrue(true);

		} catch (ServerValidationException e) {
			assertTrue(e.getMessage().equals("AUTH_NO"));
		}
		
		try {
			client.fileName = "FORBIDDEN.txt";
			Client c=new Client();
			
			clientRep.saveClient(c);

			assertTrue(true);
			

		} catch (JsonConvertException e) {
			fail();

		} catch (NoFindHostException e) {
			assertTrue(true);

		} catch (ServerValidationException e) {
			assertTrue(e.getMessage().equals("FORBIDDEN"));
		}
		
	}
	

}
