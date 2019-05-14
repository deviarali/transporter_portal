package com.transporter.exception;

/**
 * Exception class for handling database related errors.
 */
public class JsonConvertException extends FatalException {

	/**
	 * default serial version id
	 */
	private static final long serialVersionUID = 3766107935557465321L;

	public JsonConvertException() {
		super();
	}

	public JsonConvertException(String message) {
		super(message);
	}

	public JsonConvertException(String message, Throwable thrw) {
		super(message, thrw);
	}
}
