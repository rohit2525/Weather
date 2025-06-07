package org.example.weather.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents the weather forecast summary for a specific date. Includes high/low temperatures and
 * weather-related suggestions.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForecastResponse {

  /** Forecast date (day only, not timestamp). */
  private LocalDate date;

  /** Highest temperature (in Celsius) for the day. */
  private double highTemp = Double.MIN_VALUE;

  /** Lowest temperature (in Celsius) for the day. */
  private double lowTemp = Double.MAX_VALUE;

  /** Suggestions like "Carry umbrella", "Use sunscreen lotion", etc. */
  private Set<String> suggestions = new HashSet<>();

  public ForecastResponse(LocalDate date) {
    this.date = date;
  }
}
