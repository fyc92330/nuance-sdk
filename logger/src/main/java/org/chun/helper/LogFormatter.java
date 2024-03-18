package org.chun.helper;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.message.FormattedMessage;
import org.apache.logging.log4j.message.Message;

public class LogFormatter {

  public static Message formatSystem(String groupId, String logName, String message, Throwable throwable) {

    String exception = getThrowableMessage(throwable);

    return new FormattedMessage("[group.id]:{},[log.name]:{},[detail]:{},[exception]:{}", groupId, logName, message, exception);
  }


  public static Message formatApiOut(String targetId, String requestUrl, String requestMethod, String requestBody,
      Integer responseCode, String responseMessage,
      String responseBody,
      long cost, Throwable throwable) {

    String exception = getThrowableMessage(throwable);
    String format = "[target.id]:{},[request.url]:{},[request.method]:{},[request.body]:{},[response.code]:{},[response.message]:{},[response.body]:{},[cost]:{},[exception]:{}";

    return new FormattedMessage(format, targetId, requestUrl, requestMethod, requestBody, responseCode, responseMessage,
        responseBody, cost, exception);
  }


  public static Message formatApiIn(String originId, String requestIp, String requestUrl, String requestMethod,
      String requestBody, Integer responseCode, String responseMessage,
      String responseBody, long cost, Throwable throwable) {

    String exception = getThrowableMessage(throwable);
    String format = "[origin.id]:{},[request.ip]:{},[request.url]:{},[request.method]:{},[request.body]:{},[response.code]:{},[response.message]:{},[response.body]:{},[cost]:{},[exception]:{}";

    return new FormattedMessage(format, originId, requestIp, requestUrl, requestMethod, requestBody, responseCode,
        responseMessage, responseBody, cost, exception);
  }


  private static String getThrowableMessage(Throwable throwable) {

    if (null == throwable) {
      return "-";
    }

    String message = ExceptionUtils.getStackTrace(throwable);

    return ":\n" + message.substring(0, NumberUtils.min(message.length(), 5000));
  }
}
