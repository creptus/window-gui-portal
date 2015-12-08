package tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import http.IHTTPClient;
import http.Response;

public class FakeUtlabHTTPClient implements IHTTPClient {

	protected static FakeUtlabHTTPClient instanse = null;

	public static FakeUtlabHTTPClient getInstanse() {
		
		return new FakeUtlabHTTPClient();
		
		
		/*if (instanse == null) {
			instanse = new FakeUtlabHTTPClient();
		}
		return instanse;*/
	}
	
	public String fileName="ok.txt";

	@Override
	public Response post(String path, String data) {
		Response resp = new Response();
		try {
			resp.data = read("test_data/"+path+"/" + fileName);
			
			/*switch (path) {
			case "rest/client/list":
				resp.data = read("test_data/rest/client/list/" + fileName);
				// System.out.println(resp.data);
				break;
			case "rest/client/get":
				resp.data = read("test_data/rest/client/get/" + fileName);
				break;
			case "rest/client/post":
				resp.data = read("test_data/rest/client/post/" + fileName);
				break;
			default:

			}*/
		} catch (FileNotFoundException e) {
			System.out.println("Not found: " + e.getMessage());
		}
		return resp;
	}

	@Override
	public void setUrl(String url) {
		// TODO Auto-generated method stub

	}

	public String read(String fileName) throws FileNotFoundException {
		// Этот спец. объект для построения строки
		StringBuilder sb = new StringBuilder();

		File file = exists(fileName);
		
		System.out.println(fileName);

		try {
			// Объект для чтения файла в буфер
			BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
			try {
				// В цикле построчно считываем файл
				String s;
				while ((s = in.readLine()) != null) {
					sb.append(s);
					sb.append("\n");
				}
			} finally {
				// Также не забываем закрыть файл
				in.close();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		// Возвращаем полученный текст с файла
		return sb.toString();
	}

	private File exists(String fileName) throws FileNotFoundException {
		File file = new File(fileName);
		if (!file.exists()) {
			throw new FileNotFoundException(file.getAbsolutePath());
		}
		return file;

	}

}
