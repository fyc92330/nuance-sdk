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

}
