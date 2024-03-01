package gw.exception;

public class GwHttpIOException extends RuntimeException {

  public GwHttpIOException(String message) {

    super(message);
  }


  public GwHttpIOException(String message, Throwable cause) {

    super(message, cause);
  }
}
