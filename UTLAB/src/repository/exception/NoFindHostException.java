package repository.exception;

public class NoFindHostException  extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2263329143050526584L;

	public NoFindHostException () {
		super();
	}

	public NoFindHostException (String message) {
		super(message);
	}

	public NoFindHostException (String message, Throwable cause) {
		super(message, cause);
	}

	public NoFindHostException (Throwable cause) {
		super(cause);
	}
}
