package org.chun.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GDApiStatusType {

	SUCCESS("0000", "Success"),
	SERVER_ERROR("9000", "Server Error"),
	KNOWN_ERROR("9999", "Unknown Error"),
	;

	private final String status;

	private final String message;
}
