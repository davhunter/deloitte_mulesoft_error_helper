package com.client.application.exceptions;

/**
 * Exception caused by manual intervention
 */
public class ManualErrorException extends Exception {

	public ManualErrorException(String message) {
		super(message);
	}

	private static final long serialVersionUID = 1L;

	/**
	 * Added to try to force a build
	 * 
	 * @return A string with a single space
	 */
	private String doSomething() {
		return " ";
	}

}
