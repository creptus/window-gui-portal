package repository;

import java.util.Iterator;
import java.util.LinkedList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import http.IHTTPClient;
import http.Response;
import models.Project;
import repository.exception.JsonConvertException;
import repository.exception.NoFindHostException;
import repository.exception.ServerValidationException;

public class ProjectRepository {
	protected IHTTPClient client = null;

	public ProjectRepository(IHTTPClient httpClient) {
		client = httpClient;
	}

	public LinkedList<Project> getProjects(boolean in_work)
			throws JsonConvertException, NoFindHostException, ServerValidationException {
		LinkedList<Project> projects = new LinkedList<>();

		Response res = client.post("rest/project/list", "{\"in_work\": "+in_work+"}");
		//System.out.println(res.toString());
		
		if (res == null) {
			return null;
		}
		if (res.error != null) {
			// System.out.println("ERROR 1");
			throw new NoFindHostException(res.error);
		}
		try {
			if (!res.data.equals("")) {
				JSONParser parser = new JSONParser();

				Object obj = parser.parse(res.data);
				JSONObject jsonObj = (JSONObject) obj;

				String errorMsg = (String) jsonObj.get("error");
				if (errorMsg != null) {
					// System.out.println("ERROR 2");
					throw new ServerValidationException(errorMsg);
				}
				if (jsonObj.get("auth") == null || (Boolean) jsonObj.get("auth")) {
					JSONArray projects_data = (JSONArray) jsonObj.get("data");
					@SuppressWarnings("rawtypes")
					Iterator i = projects_data.iterator();
					while (i.hasNext()) {
						JSONObject project = (JSONObject) i.next();
						Project p = new Project();
						p.id = Integer.parseInt(project.get("id").toString());
						p.create_date = (String) project.get("create_date");
						p.delete_date = (String) project.get("delete_date");

						JSONObject domain = (JSONObject) project.get("domain");
						p.domain.domain = (String) domain.get("domain");
						p.domain.domain_paid_till = (String) domain.get("domain_paid_till");

						JSONObject thematic = (JSONObject) project.get("thematic");
						p.thematic.name = (String) thematic.get("name");

						JSONObject cl = (JSONObject) project.get("client");
						if (cl != null) {
							p.client.company_name = (String) cl.get("company_name");
						}

						projects.add(p);

					}
				}

			}

		} catch (ParseException e) {
			throw new JsonConvertException("ParseException " + e.getMessage() + e.getPosition());
		}

		return projects;
	}

	public Project getProject(int id) throws JsonConvertException, NoFindHostException, ServerValidationException {

		
		Project p = null;
		Response res = client.post("rest/project/get", "{\"project_id\":\"" + id + "\"}");
		// System.out.println(res.toString());
		if (res == null) {
			return null;
		}
		if (res.error == null) {
			throw new NoFindHostException();
		}
		try {
			JSONParser parser = new JSONParser();

			Object obj = parser.parse(res.data);
			JSONObject jsonObj = (JSONObject) obj;

			String errorMsg = (String) jsonObj.get("error");
			if (errorMsg != null) {
				throw new ServerValidationException(errorMsg);
			}
			if (jsonObj.get("auth") == null || (Boolean) jsonObj.get("auth")) {
				JSONObject project = (JSONObject) jsonObj.get("data");
				if (project.get("id") != null) {
					p = new Project();
					p.id = Integer.parseInt(project.get("id").toString());
					p.create_date = (String) project.get("create_date");
					p.delete_date = (String) project.get("delete_date");
					p.domain_id = Integer.parseInt(project.get("domain_id").toString());
					p.client_id = Integer.parseInt(project.get("client_id").toString());
					p.thematic_id = Integer.parseInt(project.get("thematic_id").toString());
					p.owner_id = Integer.parseInt(project.get("owner_id").toString());
					p.came_date = (String) project.get("came_date");
					p.type = (String) project.get("type");
					p.comment = (String) project.get("comment");

				}

			}

		} catch (ParseException e) {
			throw new JsonConvertException("ParseException " + e.getMessage() + e.getPosition());
		}

		return p;
	}

	public boolean saveProject(Project p){
		
		String data="{\"domain\":{\"domain\":\""+p.domain.domain+"\"}, "
				+ "\"thematic_id\":\""+p.thematic_id+"\", "
				+ "\"client_id\":\""+p.client_id+"\", "
				+ "\"type\":\""+p.type+"\"}";
		
		System.out.println(data);
		
		/*Response res = client.post("rest/project/post", data);*/
		
		
		
		return false;
		
	}

}
