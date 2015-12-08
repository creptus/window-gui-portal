package tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.LinkedList;

import org.junit.Test;


import models.Project;

import repository.ProjectRepository;
import repository.exception.JsonConvertException;
import repository.exception.NoFindHostException;
import repository.exception.ServerValidationException;

public class TestProjectRepository {
	@Test
	public void testGetProjects() {
		FakeUtlabHTTPClient client = FakeUtlabHTTPClient.getInstanse();
		ProjectRepository projectRep = new ProjectRepository(client);
		try {
			client.fileName = "ok.txt";
			LinkedList<Project> p = projectRep.getProjects(true);
			//System.out.println(p.size());
			assertTrue("Array count 0", p.size() != 0);

		} catch (JsonConvertException e) {
			fail();

		} catch (NoFindHostException e) {
			assertTrue(true);
		} catch (ServerValidationException e) {
			fail();
		}

		try {
			client.fileName = "AUTH_NO.txt";
			LinkedList<Project> p = projectRep.getProjects(true);
			assertTrue("Array count 0", p.size() == 0);
		} catch (JsonConvertException e) {
			fail();

		} catch (NoFindHostException e) {
			assertTrue(true);
		} catch (ServerValidationException e) {
			assertTrue(e.getMessage().equals("AUTH_NO"));
		}
		
		try {
			client.fileName = "FORBIDDEN.txt";
			LinkedList<Project> p = projectRep.getProjects(true);
			assertTrue("Array count 0", p.size() == 0);
		} catch (JsonConvertException e) {
			fail();

		} catch (NoFindHostException e) {
			assertTrue(true);
		} catch (ServerValidationException e) {
			assertTrue(e.getMessage().equals("FORBIDDEN"));
		}
	}
	
	@Test
	public void testGetProject() {
		FakeUtlabHTTPClient client = FakeUtlabHTTPClient.getInstanse();
		ProjectRepository projectRep = new ProjectRepository(client);
		try {
			Project p = projectRep.getProject(456);

			assertTrue(p.id == 456);
			assertTrue(p.domain_id == 416);
			

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
			projectRep.getProject(456);

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
			projectRep.getProject(456);

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
}
