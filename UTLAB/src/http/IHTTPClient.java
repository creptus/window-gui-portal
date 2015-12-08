package http;

public interface IHTTPClient {	

	Response post(String path, String data);

	public void setUrl(String url);
}
