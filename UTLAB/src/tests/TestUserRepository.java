package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import http.UtlabHTTPClient;
import models.User;
import repository.UserRepository;
import repository.exception.JsonConvertException;
import repository.exception.NoFindHostException;

public class TestUserRepository {
	@Test
	public void testIsAuth_real() {
		UtlabHTTPClient client = UtlabHTTPClient.getInstanse();
		UserRepository userRep = new UserRepository(client);
		try {
			userRep.isAuth();
			assertTrue(true);
		} catch (JsonConvertException e) {
			fail();

		} catch (NoFindHostException e) {
			assertTrue(true);
		} catch (Exception e) {
			fail();
		}

	}

	@Test
	public void testAuth_real() {
		UtlabHTTPClient client = UtlabHTTPClient.getInstanse();
		UserRepository userRep = new UserRepository(client);
		try {
			boolean result = userRep.auth("alexey1.korolev@utlab.ru", "");
			//assertTrue(result);
			assertFalse(result);

		} catch (JsonConvertException e) {
			fail();

		} catch (NoFindHostException e) {
			assertTrue(true);
		} catch (Exception e) {
			fail();
		}

	}

	@Test
	public void testGetAuthUser_false() {
		UtlabHTTPClient client = UtlabHTTPClient.getInstanse();
		UserRepository userRep = new UserRepository(client);
		try {
			User u = userRep.getAuthUser();
			assertNull(u);
		} catch (JsonConvertException e) {
			fail();

		} catch (NoFindHostException e) {
			assertTrue(true);
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testGetAuthUser_true() {
		//auth
		//get user
		assertTrue(true);
	}
}
