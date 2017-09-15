package com.client.application.exceptions;

import org.mule.module.apikit.exception.BadRequestException;

public class ManualErrorException extends BadRequestException {

	public ManualErrorException(String message) {
		super(message);
	}

	private static final long serialVersionUID = 1L;


}
