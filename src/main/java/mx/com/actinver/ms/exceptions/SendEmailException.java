package mx.com.actinver.ms.exceptions;

public class SendEmailException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4088649120307193208L;

	public SendEmailException() {
		super();
	}

	public SendEmailException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public SendEmailException(final String message) {
		super(message);
	}

	public SendEmailException(final Throwable cause) {
		super(cause);
	}

}
