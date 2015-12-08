package repository;

import java.util.Iterator;
import java.util.LinkedList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import http.IHTTPClient;
import http.Response;
import models.Client;
import repository.exception.JsonConvertException;
import repository.exception.NoFindHostException;
import repository.exception.ServerValidationException;

public class ClientRepository {
	protected IHTTPClient client = null;

	public ClientRepository(IHTTPClient httpClient) {
		client = httpClient;
	}

	public LinkedList<Client> getClients() throws JsonConvertException, NoFindHostException {
		LinkedList<Client> clients = new LinkedList<>();

		Response res = client.post("rest/client/list", "");
		// System.out.println(res.toString());
		if (res == null) {
			return null;
		}
		if (res.error == null) {
			try {
				if (!res.data.equals("")) {
					JSONParser parser = new JSONParser();

					Object obj = parser.parse(res.data);
					JSONObject jsonObj = (JSONObject) obj;

					String errorMsg = (String) jsonObj.get("error");
					if (errorMsg == null) {
						if (jsonObj.get("auth") == null || (Boolean) jsonObj.get("auth")) {
							JSONArray data = (JSONArray) jsonObj.get("data");
							@SuppressWarnings("rawtypes")
							Iterator i = data.iterator();
							while (i.hasNext()) {
								JSONObject cl = (JSONObject) i.next();
								clients.add(new Client(Integer.parseInt(cl.get("id").toString()),
										(String) cl.get("company_name"), (String) cl.get("inn")));
							}
							return clients;
						}

					} else {
						// throw new Exception(errorMsg);

					}
				}

			} catch (ParseException e) {
				throw new JsonConvertException("ParseException " + e.getMessage() + e.getPosition());
			}
		}

		return clients;
	}

	public Client getClient(int id) throws JsonConvertException, NoFindHostException, ServerValidationException {
		Client c = null;
		Response res = client.post("rest/client/get", "{\"client_id\":\"" + id + "\"}");
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
				if (errorMsg != null) {
					throw new ServerValidationException(errorMsg);
				}
				if (jsonObj.get("auth") == null || (Boolean) jsonObj.get("auth")) {
					JSONObject data = (JSONObject) jsonObj.get("data");
					if (data.get("id") != null) {
						c = new Client();
						c.id = Integer.parseInt(data.get("id").toString());
						c.company_name = (String) data.get("company_name");
						c.inn = (String) data.get("inn");
						c.login = (String) data.get("login");
						c.seller_id = Integer.parseInt(data.get("seller_id").toString());
						JSONObject seller = (JSONObject) data.get("seller");
						if(seller!=null){
							c.seller.id = Integer.parseInt(seller.get("id").toString());
							c.seller.company_name = (String) seller.get("company_name");
						}
						

						// System.out.println((String) data.get("inn"));
					}
					return c;
				}

			} catch (ParseException e) {
				throw new JsonConvertException("ParseException " + e.getMessage() + e.getPosition());
			}
		}
		return c;
	}

	public void saveClient(Client c) throws JsonConvertException, NoFindHostException, ServerValidationException {
		Response res = client.post("rest/client/post", c.toJSON());
		System.out.println(res);
		// System.out.println(res.toString());
		if (res == null) {
			return;
		}

		if (res.error == null) {
			try {
				JSONParser parser = new JSONParser();

				Object obj = parser.parse(res.data);
				JSONObject jsonObj = (JSONObject) obj;

				String errorMsg = (String) jsonObj.get("error");
				if (errorMsg != null) {
					throw new ServerValidationException(errorMsg);
				}

			} catch (ParseException e) {
				throw new JsonConvertException("ParseException " + e.getMessage() + e.getPosition()+"  "+res.toString());
			}
		} else {
			throw new NoFindHostException();
		}

	}
}
