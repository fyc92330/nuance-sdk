package org.chun.exception;

import org.chun.enums.GdsStatusType;

public class GdsApplicationReadyCacheException extends GdsBaseException {

	public GdsApplicationReadyCacheException(String message, Throwable cause) {

		super(GdsStatusType.UNKNOWN_ERROR, message, cause);
	}

}
