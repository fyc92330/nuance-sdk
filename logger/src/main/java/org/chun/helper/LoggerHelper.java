package org.chun.helper;

import org.chun.constants.LoggerType;

import java.util.logging.Logger;

public class LoggerHelper extends Logger {

	public LoggerHelper(LoggerType type) {

		super(type.getPrefix(), type.name());
	}



}
