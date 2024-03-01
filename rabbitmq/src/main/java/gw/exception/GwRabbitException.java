package gw.exception;

public class GwRabbitException extends RuntimeException {

  public GwRabbitException(String message) {

    super(message);
  }


  public GwRabbitException(String message, Throwable cause) {

    super(message, cause);
  }
}
