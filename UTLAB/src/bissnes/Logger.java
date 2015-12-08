package bissnes;

import java.util.ArrayList;
import java.util.Calendar;

public class Logger {
	protected Logger() {

	}

	protected static Logger instance = null;

	public static Logger getInstance() {
		if (instance == null) {
			instance = new Logger();
		}
		return instance;
	}
	
	ArrayList<String> list = new ArrayList<String>();	
	public void log(String message){
		Calendar cal = Calendar.getInstance();
		
		list.add(cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND)+" "+message);
	}
	
	public String getMessage(){
		if(list.size()>0){
			String result=list.get(0);
			list.remove(0);
			return result;
		}else{
			return null;
		}
	}
}
