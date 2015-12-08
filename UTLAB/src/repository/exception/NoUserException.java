package repository.exception;

public class NoUserException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3352790177697070028L;

	public NoUserException() {
		super();
	}

	public NoUserException(String message) {
		super(message);
	}

	public NoUserException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoUserException(Throwable cause) {
		super(cause);
	}
}
