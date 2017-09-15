package com.client.application.exceptions;

import org.mule.module.apikit.exception.BadRequestException;

public class ManualErrorException extends BadRequestException {

	private static final long serialVersionUID = 1L;

	public ManualErrorException(Throwable e) {
		super(e);
	}

}
