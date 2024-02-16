package org.chun.helper;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.MessageFactory;
import org.apache.logging.log4j.spi.ExtendedLogger;
import org.apache.logging.log4j.spi.ExtendedLoggerWrapper;
import org.chun.constants.LoggerType;

public class LoggerHelper extends ExtendedLoggerWrapper {

  public static String APP_SOURCE_ID;


  public LoggerHelper(Logger logger, String name, MessageFactory messageFactory) {

    super((ExtendedLogger) logger, name, messageFactory);
  }


  public static LoggerHelper create(final LoggerType type) {

    String LoggerTypeName = type.name();
    final Logger logger = LogManager.getLogger(LoggerTypeName);
    return new LoggerHelper(logger, LoggerTypeName, logger.getMessageFactory());
  }


  @Override
  public void logMessage(final String fqcn, final Level level, final Marker marker, final Message message, final Throwable t) {

    String formattedLog = this.getLogMessage(APP_SOURCE_ID, logger.getName(), message.getFormattedMessage(), t);
    MessageFactory messageFactory = logger.getMessageFactory();
    Message newMessage = messageFactory.newMessage(formattedLog, message.getParameters());
    super.logMessage(fqcn, level, marker, newMessage, null);
  }


  private String getLogMessage(String sourceId, String logType, String message, Throwable throwable) {

    StringBuilder logBuilder = new StringBuilder();

    logBuilder.append("[sourceId]:").append(sourceId)
        .append(",[logType]:").append(logType)
        .append(",[detail]:").append(message)
        .append(",[exception]:-");

    if (throwable != null) {

      String errorMessage = ExceptionUtils.getStackTrace(throwable);
      logBuilder.append("\n");
      logBuilder.append(errorMessage, 0, NumberUtils.min(message.length(), 5000));
    }

    return logBuilder.toString();
  }
}
