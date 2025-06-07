package org.example.weather.exception;

import java.io.Serial;

public class WeatherDataUnavailableException extends RuntimeException {
  @Serial private static final long serialVersionUID = 2L;

  public WeatherDataUnavailableException(String message) {
    super(message);
  }

  public WeatherDataUnavailableException(String message, Throwable cause) {
    super(message, cause);
  }
}
