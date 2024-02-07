package org.chun.exception;

public class GdsJsonException extends RuntimeException {

  public GdsJsonException(String message) {

    super(message);
  }


  public GdsJsonException(String message, Throwable cause) {

    super(message, cause);
  }
}
