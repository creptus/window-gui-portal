package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import http.Response;

public class TestFakeUtlabHTTPRepository {

	@Test
	public void testPost() {
		FakeUtlabHTTPClient client = FakeUtlabHTTPClient.getInstanse();
		Response resp = client.post("rest/client/list", "");

		assertNotNull(resp);
		assertFalse(resp.data.equals(""));
	}

}
