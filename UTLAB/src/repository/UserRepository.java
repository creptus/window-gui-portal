package repository;

import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import http.IHTTPClient;
import http.Response;
import models.Permission;
import models.User;
import repository.exception.JsonConvertException;
import repository.exception.NoFindHostException;

public class UserRepository {
	protected IHTTPClient client = null;

	public UserRepository(IHTTPClient httpClient) {
		client = httpClient;
	}

	public User getAuthUser() throws JsonConvertException, NoFindHostException {
		User u = null;
		Response res = client.post("rest/users/auth-user", "");
		// System.out.println(res.toString());
		if (res == null) {
			return null;
		}

		if (res.error == null) {
			try {
				JSONParser parser = new JSONParser();

				Object obj = parser.parse(res.data);
				JSONObject jsonObj = (JSONObject) obj;

				String errorMsg = (String) jsonObj.get("error");
				if (errorMsg == null) {
					JSONObject data = (JSONObject) jsonObj.get("data");
					if ((Boolean) jsonObj.get("auth")) {
						u = new User();
						u.id = Integer.parseInt((String) data.get("id"));
						u.login = (String) data.get("login");
						u.email = (String) data.get("email");
						u.is_deleted = Integer.parseInt((String) data.get("is_deleted"));
						u.is_system = Integer.parseInt((String) data.get("is_system"));
						u.created_at = (String) data.get("created_at");
						u.updated_at = (String) data.get("updated_at");

						JSONArray permissions = (JSONArray) jsonObj.get("permissions");
						@SuppressWarnings("rawtypes")
						Iterator i = permissions.iterator();
						 while (i.hasNext()) {
							 JSONObject perm = (JSONObject) i.next();					            
					            // Here I try to take the title element from my slide but it doesn't work!
					            String name = (String) perm.get("name");
					            int is_writeble =Integer.parseInt((String) perm.get("is_writeble"));					            
					            u.permission.add(new Permission(0,name,is_writeble));					            
					        }
						return u;
					}

				} else {
					// throw new Exception(errorMsg);

				}
			} catch (ParseException e) {
				throw new JsonConvertException("ParseException " + e.getMessage() + e.getPosition());
			}
		}
		return u;
	}

	public boolean auth(String login, String password) throws JsonConvertException, NoFindHostException {
		Response res = client.post("rest/users/auth",
				"{\"email\":\"" + login + "\", \"password\":\"" + password + "\"}");
		// System.out.println(res.toString());
		if (res == null) {
			return false;
		}
		if (res.error == null) {
			try {
				JSONParser parser = new JSONParser();

				Object obj = parser.parse(res.data);
				JSONObject jsonObj = (JSONObject) obj;

				String errorMsg = (String) jsonObj.get("error");
				if (errorMsg == null) {
					if ((Boolean) jsonObj.get("auth")) {
						return true;
					}

				} else {
					// throw new Exception(errorMsg);

				}
			} catch (ParseException e) {
				// throw new JsonConvertException(e.getMessage() +
				// e.getPosition());
				throw new JsonConvertException(e.getMessage() + e.getPosition());
			}
		} else {
			if (res.error.equals("NO_FIND_HOST")) {
				throw new NoFindHostException();
			}
		}

		return false;
	}

	public boolean isAuth() throws JsonConvertException, NoFindHostException {
		Response res = client.post("rest/users/auth-user", "");
		// System.out.println(res.toString());
		if (res != null) {

			if (res.error == null) {
				try {
					JSONParser parser = new JSONParser();

					Object obj = parser.parse(res.data);
					JSONObject jsonObj = (JSONObject) obj;
					String errorMsg = jsonObj.get("error").toString();
					if (errorMsg.equals("")) {
						if ((Boolean) jsonObj.get("auth")) {
							return true;
						}

					} else {
						// throw new Exception(errorMsg);
					}
				} catch (ParseException e) {
					throw new JsonConvertException();
				}
			} else {
				// error AUTH_NO
				// throw new Exception(res.error);
			}
		}
		return false;
	}
}
