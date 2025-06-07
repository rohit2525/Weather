package org.example.weather.exception;

public class CityNotFoundException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public CityNotFoundException(String message) {
    super(message);
  }

  public CityNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
