package repository.exception;

public class JsonConvertException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8970139268111572714L;

	public JsonConvertException() {
		super();
	}

	public JsonConvertException(String message) {
		super(message);
	}

	public JsonConvertException(String message, Throwable cause) {
		super(message, cause);
	}

	public JsonConvertException(Throwable cause) {
		super(cause);
	}

}
