package org.example.weather.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/** Configuration class to hold all weather API related properties. */
@Data
@Configuration
@ConfigurationProperties(prefix = "weather.api")
public class WeatherAppProperties {

  /** OpenWeatherMap API key. */
  private String key;

  /** Base URL for fetching forecast data. */
  private String url;

  /** Number of 3-hour intervals to fetch. 24 = 3 days. */
  private int cnt;

  /** Path to mock file used during fallback/offline mode. */
  private String fallbackPath;

  private boolean offlineMode;
}
