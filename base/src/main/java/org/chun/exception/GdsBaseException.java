package org.chun.exception;

import lombok.Getter;
import org.chun.enums.GdsStatusType;

@Getter
public class GdsBaseException extends RuntimeException {

	private final GdsStatusType type;

	public GdsBaseException(GdsStatusType type) {

		super(type.getMessage());
		this.type = type;
	}

	public GdsBaseException(GdsStatusType type, String message) {

		super(message);
		this.type = type;
	}

	public GdsBaseException(GdsStatusType type, Throwable cause) {

		super(type.getMessage(), cause);
		this.type = type;
	}

	public GdsBaseException(GdsStatusType type, String message, Throwable cause) {

		super(message, cause);
		this.type = type;
	}

}
