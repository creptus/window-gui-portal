package http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

public class UtlabHTTPClient implements IHTTPClient {

	protected UtlabHTTPClient() {
	}

	protected static String cookie = "";

	protected static UtlabHTTPClient instanse = null;

	public static UtlabHTTPClient getInstanse() {
		if (instanse == null) {
			instanse = new UtlabHTTPClient();
		}
		return instanse;
	}

	protected String mainUrl = "http://lab2.utlab.loc/";

	public void setUrl(String url) {
		mainUrl = url;
	}

	public Response post(String path, String data) {
		System.out.println("POST: "+mainUrl + path);
		HttpURLConnection connection = null;
		Response res = new Response();
		try {

			// Create connection
			URL url = new URL(mainUrl + path);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");

			connection.setRequestProperty("Content-Length", Integer.toString(data.getBytes().length));
			// connection.setRequestProperty("Content-Language", "en-US");

			String myCookie = cookie;
			connection.setRequestProperty("Cookie", myCookie);
			connection.setUseCaches(false);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());

			// System.out.println("Default Charset=" +
			// Charset.defaultCharset());
			// wr.writeBytes(data);
			res.queryData=data;
			wr.write(data.getBytes("UTF8"));
			wr.close();
			
			

			int code = connection.getResponseCode();
			res.statusCode = code;

			for (int i = 0;; i++) {
				String headerName = connection.getHeaderFieldKey(i);
				String headerValue = connection.getHeaderField(i);

				if (headerName == null && headerValue == null) {
					break;
				}
				// System.out.println("HeaderFields: ");
				// System.out.println(headerName);
				// System.out.println(headerValue);
				if ("Set-Cookie".equalsIgnoreCase(headerName)) {
					cookie = headerValue;
					// System.out.println("SET OK");
				}
				//
				//
			}

			if (code == 200) {
				// Get Response
				InputStream is = connection.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				//System.out.println(isr.getEncoding());
				res.encoding=isr.getEncoding();
				BufferedReader rd = new BufferedReader(isr);
				
				StringBuilder response = new StringBuilder(); // or StringBuffer
																// if not Java
																// 5+
				String line;
				while ((line = rd.readLine()) != null) {
					response.append(line);
					response.append('\r');
				}
				rd.close();
				res.data = response.toString();
				System.out.println("OK "+mainUrl + path);
			}

			return res;// -----------------------------------------------------

		} catch (UnknownHostException e) {
			res.error = "NO_FIND_HOST";
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}
}
