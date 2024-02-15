package org.chun.exception;

import org.chun.constants.GdsStatusType;

public class GdsLineClientException extends GdsBaseException {

	public GdsLineClientException(GdsStatusType type) {

		super(type);
	}

}
