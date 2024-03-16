package org.chun.exception;

import org.chun.enums.GDApiStatusType;

public class GDJsonException extends GDBaseException {

	public GDJsonException(Throwable e) {

		super(GDApiStatusType.SERVER_ERROR, e);
	}

}
