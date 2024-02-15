package org.chun.exception;

import org.chun.constants.GdsStatusType;

public class GdsJsonException extends GdsBaseException {


	public GdsJsonException(String message, Throwable cause) {

		super(GdsStatusType.UNKNOWN_ERROR, message, cause);
	}

}
