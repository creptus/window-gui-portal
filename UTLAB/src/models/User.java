package models;

import java.util.LinkedList;
import java.util.List;

public class User {

	public int id = 0;
	public String login = "";
	public String email = "";

	public int is_deleted = 0;

	public int is_system = 0;

	public String created_at = "";

	public String updated_at = "";
	
	public  List<Permission> permission = new LinkedList<>();

}
