package tests;

import static org.junit.Assert.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;

import http.Response;
import http.UtlabHTTPClient;

public class TestUtlabHTTPClient {

	@Test
	public void client() {
		UtlabHTTPClient client = UtlabHTTPClient.getInstanse();
		assertNotNull(client);
		// assertTrue(true);
	}

	@Test
	public void helloGoogle() {
		UtlabHTTPClient client = UtlabHTTPClient.getInstanse();
		client.setUrl("http://google.ru");
		try {
			Response res = client.post("", "");
			assertNotNull(res);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void helloLab2() {
		UtlabHTTPClient client = UtlabHTTPClient.getInstanse();
		client.setUrl("http://lab2.utlab.loc/");
		try {
			Response res = client.post("", "");

			if (res != null) {
				if (res.error != null) {
					System.out.println(res.statusCode);
					System.out.println(res.error);
				}

				assertNull(res.error);
			} else {
				fail();
			}

		} catch (Exception e) {
			fail();
		}
	}
	@Test
	public void lab2Auth() {
		UtlabHTTPClient client = UtlabHTTPClient.getInstanse();
		client.setUrl("http://lab2.utlab.loc/");
		Response res = client.post("rest/project/list", "");
		
		if(res!=null){
			System.out.println(res.statusCode);
			System.out.println(res.data);	
			try{
				JSONParser parser = new JSONParser();

				Object obj = parser.parse(res.data);
				JSONObject jsonObj = (JSONObject) obj;
				System.out.println(jsonObj.get("error"));
				
			}catch(Exception e){}
			assertNull(res.error);			
		}else{
			fail();
		}		
	}

}
