package gw.exception;

import lombok.Data;

@Data
public class GwHttpException extends RuntimeException {

  private final int code;


  public GwHttpException(int code, String message) {

    super(message);
    this.code = code;
  }


  public GwHttpException(int code, String message, Throwable cause) {

    super(message, cause);
    this.code = code;
  }
}
