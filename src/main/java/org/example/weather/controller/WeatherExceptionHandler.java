package org.example.weather.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.weather.exception.CityNotFoundException;
import org.example.weather.exception.ErrorResponse;
import org.example.weather.exception.WeatherDataUnavailableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for the Weather application. This class uses @RestControllerAdvice to
 * provide centralized exception handling across all @Controller and @RestController classes. It
 * catches specific exceptions and maps them to appropriate HTTP status codes and custom error
 * response bodies.
 */
@RestControllerAdvice
@Slf4j
public class WeatherExceptionHandler {

  /**
   * Handles {@link CityNotFoundException}. This method is invoked when a CityNotFoundException is
   * thrown anywhere in the application's controller layer. It returns an HTTP 404 Not Found status
   * with a custom error message.
   *
   * @param ex The CityNotFoundException that was thrown.
   * @return A ResponseEntity containing an ErrorResponse and HTTP status 404.
   */
  @ExceptionHandler({CityNotFoundException.class, WeatherDataUnavailableException.class})
  public ResponseEntity<ErrorResponse> handleCityNotFound(CityNotFoundException ex) {
    log.warn("CityNotFoundException caught: {}", ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND) // Set HTTP status to 404
        .body(new ErrorResponse(ex.getMessage())); // Custom error response body
  }

  /**
   * Generic exception handler for any unhandled exceptions. This method acts as a fallback for any
   * exceptions not specifically handled by other @ExceptionHandler methods. It returns an HTTP 500
   * Internal Server Error status with a generic error message.
   *
   * @param ex The Exception that was thrown.
   * @return A ResponseEntity containing an ErrorResponse and HTTP status 500.
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
    log.error(
        "An unexpected error occurred: {}",
        ex.getMessage(),
        ex); // Log full stack trace for generic errors
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) // Set HTTP status to 500
        .body(new ErrorResponse("An unexpected error occurred. Please try again later."));
  }
}
