package com.deloitte.capi.exceptions;

/**
 * Raised when an invalid source is specified. Should never happen.
 * 
 * @author David Hunter (Deloitte)
 *
 */
public class InvalidSourceException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public InvalidSourceException(String message) {
		super(message);
	}

}
