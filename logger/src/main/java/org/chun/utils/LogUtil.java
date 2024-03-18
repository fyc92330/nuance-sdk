package org.chun.utils;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.chun.handler.ElkLogger;

public class LogUtil {

  public static final Logger SYSTEM = new ElkLogger(LogManager.getLogger("system"));
}
