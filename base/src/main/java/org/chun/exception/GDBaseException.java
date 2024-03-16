package org.chun.exception;

import lombok.Getter;
import org.chun.enums.GDApiStatusType;

@Getter
public abstract class GDBaseException extends RuntimeException {

	private final String status;

	private final String message;

	public GDBaseException(GDApiStatusType statusType) {

		this.status = statusType.getStatus();
		this.message = statusType.getMessage();
	}

	public GDBaseException(GDApiStatusType statusType, String message) {

		this.status = statusType.getStatus();
		this.message = message;
	}

	public GDBaseException(GDApiStatusType statusType, Throwable e) {

		this.status = statusType.getStatus();
		this.message = e.getMessage();
	}

	public GDBaseException(GDApiStatusType statusType, String message, Throwable e) {

		this.status = statusType.getStatus();
		this.message = message + " Exception: " + e.getMessage();
	}

}
