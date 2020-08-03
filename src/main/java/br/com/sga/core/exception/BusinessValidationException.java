package br.com.sga.core.exception;

public class BusinessValidationException extends Exception {

	private static final long serialVersionUID = -7787777129705162997L;

	private String fieldId;
	private String messageKey;
	private String defaultMessage;

	public BusinessValidationException() {
		super();
	}

	public BusinessValidationException(String message) {
		super(message);
	}
	
	public BusinessValidationException(String fieldId, String messageKey) {
		super();
		this.fieldId = fieldId;
		this.messageKey = messageKey;
		this.defaultMessage = "";
	}
	
	public BusinessValidationException(String fieldId, String messageKey, String defaultMessage) {
		super();
		this.fieldId = fieldId;
		this.messageKey = messageKey;
		this.defaultMessage = defaultMessage;
	}
	
	public BusinessValidationException(String fieldId, String messageKey, String defaultMessage, String message) {
		super(message);
		this.fieldId = fieldId;
		this.messageKey = messageKey;
		this.defaultMessage = defaultMessage;
	}

	public BusinessValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessValidationException(Throwable cause) {
		super(cause);
	}

	protected BusinessValidationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public String getFieldId() {
		return fieldId;
	}

	public String getMessageKey() {
		return messageKey;
	}

	public String getDefaultMessage() {
		return defaultMessage;
	}
	
}