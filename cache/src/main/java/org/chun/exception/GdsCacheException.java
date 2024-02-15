package org.chun.exception;

import org.chun.constants.GdsStatusType;

public class GdsCacheException extends GdsBaseException {

	public GdsCacheException(String message, Throwable cause) {

		super(GdsStatusType.UNKNOWN_ERROR, message, cause);
	}

}
