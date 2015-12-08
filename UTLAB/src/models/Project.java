package models;

public class Project {
	public int id = 0;
	public int domain_id = 0;
	public int client_id = 0;
	public int thematic_id = 0;
	public int owner_id = 0;
	public String create_date = "";
	public String delete_date = "";
	public String came_date = "";
	public String type = "SEO";
	public String comment = "";
	
	public Domain domain=new Domain();
	public Client client=new Client();
	public Thematic thematic=new Thematic();

}
