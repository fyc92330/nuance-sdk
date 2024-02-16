package org.chun.exception;

import org.chun.constants.GdsStatusType;

public class GdsRedisException extends GdsBaseException {


  public GdsRedisException(String message) {

    super(GdsStatusType.UNKNOWN_ERROR, message);
  }
}
