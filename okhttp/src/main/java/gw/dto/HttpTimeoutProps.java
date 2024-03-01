package gw.dto;

import lombok.Data;

@Data
public class HttpTimeoutProps {

  private int connectTimeout;

  private int socketTimeout;

}
