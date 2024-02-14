package org.chun.exception;

import org.chun.enums.GdsStatusType;

public class GdsLineClientException extends GdsBaseException {

	public GdsLineClientException(GdsStatusType type) {

		super(type);
	}

}
