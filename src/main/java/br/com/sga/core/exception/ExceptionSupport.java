package br.com.sga.core.exception;

public class ExceptionSupport {

	private ExceptionSupport() {
	}

	public static String getExceptionDetails(Throwable throwable) {
		StringBuilder detailedExceptionMessage = new StringBuilder();
		while (throwable != null) {
			detailedExceptionMessage
					.append(throwable.getMessage() != null ? throwable.getMessage() : "Exception message not present.");
			throwable = throwable.getCause();
		}
		return detailedExceptionMessage.toString();
	}
}
