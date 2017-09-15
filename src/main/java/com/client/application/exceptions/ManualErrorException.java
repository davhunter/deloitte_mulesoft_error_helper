package com.client.application.exceptions;

/**
 * Exception caused by manual intervention
 */
public class ManualErrorException extends Exception {

	public ManualErrorException(String message) {
		super(message);
	}

	private static final long serialVersionUID = 1L;

}
