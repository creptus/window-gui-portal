package models;

public class Permission {
	
	public Permission(){}
	
	public Permission(int _id, String _name, int _is_writeble){
		id=_id;
		name=_name;
		is_writeble=_is_writeble;
	}

	public int id = 0;
	public String name = "";
	public int is_writeble = 0;

}
