package org.chun.utils;

import org.chun.enums.LoggerType;
import org.chun.helper.LoggerHelper;
import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;

import java.util.logging.Logger;

public class LogUtil {

	public static final Logger SYSTEM = new LoggerHelper(LoggerType.SYSTEM);

}
