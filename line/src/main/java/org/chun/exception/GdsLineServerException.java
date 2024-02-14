package org.chun.exception;

import org.chun.enums.GdsStatusType;

public class GdsLineServerException extends GdsBaseException {

	public GdsLineServerException(GdsStatusType type) {

		super(type);
	}

}
