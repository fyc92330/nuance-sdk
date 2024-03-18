package org.chun.handler;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.spi.ExtendedLogger;
import org.apache.logging.log4j.spi.ExtendedLoggerWrapper;
import org.chun.helper.LogFormatter;

public class ElkLogger extends ExtendedLoggerWrapper {

  public static String APP_ID;


  public ElkLogger(Logger logger) {

    super((ExtendedLogger) logger, logger.getName(), logger.getMessageFactory());
  }


  public Message getFormatter(Message message, Throwable t) {

    return LogFormatter.formatSystem(APP_ID, logger.getName(), message.getFormattedMessage(), t);
  }


  @Override
  public void logMessage(final String fqcn, final Level level, final Marker marker, final Message message, final Throwable t) {

    super.logMessage(fqcn, level, marker, getFormatter(message, t), null);
  }
}
