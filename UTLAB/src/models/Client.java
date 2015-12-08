package models;

public class Client {
	public Client(){}
	public Client(int id,String company_name,String inn){
		this.id=id;
		this.company_name=company_name;
		this.inn=inn;
	}
	
	public int id = 0;
	public String login = "";
	public String password = "";
	public String company_name = "";
	public String inn = "";
	public int seller_id=22;
	public Seller seller=new Seller();
	

	@Override
	public String toString() {
	    String s = "";
	    s="login: "+login+" company_name: "+company_name+" inn: "+inn+" id: "+id;
	    return s;
	}
	
	public String toJSON(){
		String s="{\"client\":"
				+ "{"
				+ "\"id\":\""+Integer.toString(this.id)+"\","
				+ "\"seller_id\":\""+Integer.toString(this.seller_id)+"\","
				+ "\"title\":\""+this.company_name+"\","
				+ "\"company_name\":\""+this.company_name+"\","
						+ "\"password\":\""+this.password+"\","
								+ "\"login\":\""+this.login+"\","
										+ "\"inn\":\""+this.inn+"\"}}";
		return s;
	}

}
