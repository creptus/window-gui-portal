package http;

public class Response {
	/**
	 * http status code
	 */
	public int statusCode = 0;

	/**
	 * Data(body) from request
	 */
	public String data = "";

	public String error = null;
	
	public String encoding=null;
	
	public String queryData="";
	
	@Override
	public String toString() {
	    String s = "";
	    s="StatusCode: "+statusCode+" error: "+error+" data: "+data+" encoding: "+encoding+" queryData:"+queryData;
	    return s;
	}
	
}
