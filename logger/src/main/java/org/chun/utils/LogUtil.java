package org.chun.utils;

import org.apache.logging.log4j.Logger;
import org.chun.constants.LoggerType;
import org.chun.helper.LoggerHelper;


public class LogUtil {

  public static final Logger SYSTEM = LoggerHelper.create(LoggerType.SYSTEM);

  public static final Logger CACHE = LoggerHelper.create(LoggerType.CACHE);

  public static final Logger RABBIT = LoggerHelper.create(LoggerType.RABBIT);

  public static final Logger DATABASE = LoggerHelper.create(LoggerType.DATABASE);

  public static final Logger CIPHER = LoggerHelper.create(LoggerType.CIPHER);

  public static final Logger PARSER = LoggerHelper.create(LoggerType.PARSER);

  public static final Logger SCHEDULE = LoggerHelper.create(LoggerType.SCHEDULE);

  public static final Logger THIRD_PARTY = LoggerHelper.create(LoggerType.THIRD_PARTY);


}
