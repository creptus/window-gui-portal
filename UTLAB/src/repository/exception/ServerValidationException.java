package repository.exception;

public class ServerValidationException  extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2192429052298483464L;

	public ServerValidationException () {
		super();
	}

	public ServerValidationException (String message) {
		super(message);
	}

	public ServerValidationException (String message, Throwable cause) {
		super(message, cause);
	}

	public ServerValidationException (Throwable cause) {
		super(cause);
	}
}
