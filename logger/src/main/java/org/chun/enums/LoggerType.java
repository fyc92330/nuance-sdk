package org.chun.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoggerType {

	SYSTEM("system:", "[]"),
	;

	private final String prefix;
	private final String pattern;
}
