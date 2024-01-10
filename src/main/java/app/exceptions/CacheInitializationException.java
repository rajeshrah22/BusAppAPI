package app.exceptions;

public class CacheInitializationException extends Exception {
	private static final long serialVersionUID = 1855166014798781003L;

	public CacheInitializationException(String message) {
		super(message);
	}
}