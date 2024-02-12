package org.chun.exception;

import lombok.Getter;
import org.chun.enums.GdsStatusType;

@Getter
public class GdsCoreException extends Exception {

	private final GdsStatusType type;

	public GdsCoreException(GdsStatusType type) {

		super(type.getMessage());
		this.type = type;
	}

	public GdsCoreException(GdsStatusType type, String message) {

		super(message);
		this.type = type;
	}

}
