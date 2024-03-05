package org.chun.exception;

import org.chun.constants.GdsStatusType;

public class GdsCacheInitException extends GdsBaseException {

	public GdsCacheInitException(String message, Throwable cause) {

		super(GdsStatusType.UNKNOWN_ERROR, message, cause);
	}

}
