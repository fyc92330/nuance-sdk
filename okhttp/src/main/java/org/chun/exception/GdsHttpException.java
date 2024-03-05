package org.chun.exception;

import org.chun.constants.GdsStatusType;

public class GdsHttpException extends GdsBaseException {

	public GdsHttpException(String message) {

		super(GdsStatusType.UNKNOWN_ERROR, message);
	}

}
