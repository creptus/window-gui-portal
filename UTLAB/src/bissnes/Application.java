package bissnes;

import java.util.LinkedList;

import http.IHTTPClient;
import http.UtlabHTTPClient;
import models.Client;
import models.Project;
import models.User;
import repository.ClientRepository;
import repository.ProjectRepository;
import repository.UserRepository;
import repository.exception.JsonConvertException;
import repository.exception.NoFindHostException;
import repository.exception.ServerValidationException;

public class Application {
	protected Application() {

	}

	protected static Application instance = null;
	/**
	 * Клиент для http запросов
	 */
	protected static IHTTPClient client = UtlabHTTPClient.getInstanse();

	public static Application getInstance() {
		if (instance == null) {
			// checkAuth();			
			instance = new Application();

		}
		return instance;
	}

	protected static Logger logger = Logger.getInstance();

	/**
	 * Запускаю проверку пользователя
	 */
	public static void checkAuth() {
		UserRepository userRep = new UserRepository(client);
		try {
			_auth = userRep.isAuth();
		} catch (Exception e) {

			logger.log(e.getMessage());
		}
	}

	protected User user = null;
	protected static boolean _auth = false;

	/**
	 * Авторизован ли пользователь
	 * 
	 * @return
	 */
	public boolean isAuth() {
		return _auth;

	}

	public User getAuthUser() {
		return user;
	}

	/**
	 * Авторизация пользователя
	 * 
	 * @param login
	 * @param password
	 * @return
	 */
	public boolean auth(String login, String password) {
		/*
		 * String fullClassName =
		 * Thread.currentThread().getStackTrace()[2].getClassName(); String
		 * className = fullClassName.substring(fullClassName.lastIndexOf(".") +
		 * 1); String methodName =
		 * Thread.currentThread().getStackTrace()[2].getMethodName(); int
		 * lineNumber =
		 * Thread.currentThread().getStackTrace()[2].getLineNumber();
		 * 
		 * System.out.println(className + "." + methodName + "():" + lineNumber+
		 * "  "+ e.getMessage()); System.out.println("IN APP: " +
		 * e.getMessage());
		 */
		UserRepository userRep = new UserRepository(client);
		boolean result = false;
		try {
			result = userRep.auth(login, password);

			if (result) {
				user = userRep.getAuthUser();
			}

			return result;
		} catch (JsonConvertException e) {

		} catch (NoFindHostException e) {
			Logger l = Logger.getInstance();
			l.log("Host is unavailable.");
		}
		return result;
	}

	public LinkedList<Client> getClients() {
		ClientRepository clientRep = new ClientRepository(client);
		LinkedList<Client> result = null;
		Logger l = Logger.getInstance();
		try {
			result = clientRep.getClients();

		} catch (JsonConvertException e) {
			result = new LinkedList<>();

		} catch (NoFindHostException e) {
			result = new LinkedList<>();
			l.log("Host is unavailable.");
		}
		l.log("Load list clients.");
		return result;

	}

	public Client getClient(int id) {
		ClientRepository clientRep = new ClientRepository(client);
		Client result = null;

		try {
			result = clientRep.getClient(id);
			logger.log("Load client #" + id + " OK");

		} catch (JsonConvertException e) {
			result = new Client();
			logger.log("Client parse ERROR");

		} catch (NoFindHostException e) {
			result = new Client();
			logger.log("Host is unavailable.");
		}catch(ServerValidationException e){
			result = new Client();
			logger.log("Client: "+e.getMessage());
		}

		return result;
	}

	public boolean saveClient(Client c) {		
		ClientRepository clientRep = new ClientRepository(client);
		boolean result = false;
		try {
			clientRep.saveClient(c);

			if (c.id == 0) {
				logger.log("Add client OK");
			} else {
				logger.log("Save client #" + c.id + " OK");
			}
			result = true;

		} catch (JsonConvertException e) {
			logger.log("Client parse ERROR "+e.getMessage());

		} catch (NoFindHostException e) {
			logger.log("Host is unavailable.");
		} catch (ServerValidationException e) {
			logger.log("Client: " + e.getMessage());
		}
		return result;

	}

	public LinkedList<Project> getProjects(boolean in_work) {
		ProjectRepository projectRep = new ProjectRepository(client);
		LinkedList<Project> result = null;
		Logger l = Logger.getInstance();
		try {
			result = projectRep.getProjects(in_work);
			if(result == null){
				result = new LinkedList<>();
			}

		} catch (JsonConvertException e) {
			result = new LinkedList<>();

		} catch (NoFindHostException e) {
			result = new LinkedList<>();
			l.log("Host is unavailable.");
		} catch (ServerValidationException e) {
			l.log("Project: "+e.getMessage());
		}
		l.log("Load list projects.");
		return result;
	}
	
	
	

}
