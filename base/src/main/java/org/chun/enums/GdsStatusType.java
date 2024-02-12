package org.chun.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GdsStatusType {

	SUCCESS("0000", "Success."),
	;

	private final String statusCode;

	private final String message;
}