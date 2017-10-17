package com.deloitte.customerapi.exceptions;

/**
 * Raised when customers can't be found in the system
 * 
 * @author David Hunter (Deloitte)
 *
 */
public class CustomerNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public CustomerNotFoundException(String message) {
		super(message);
	}

}
