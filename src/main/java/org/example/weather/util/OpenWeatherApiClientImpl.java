package org.example.weather.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.weather.config.WeatherAppProperties;
import org.example.weather.exception.WeatherDataUnavailableException;
import org.example.weather.model.api.WeatherApiResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Client to fetch weather forecast from OpenWeatherMap API or fallback JSON file in offline mode.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class OpenWeatherApiClientImpl implements OpenWeatherApiClient {

  private final WebClient.Builder webClientBuilder;
  private final WeatherAppProperties properties;
  private final ObjectMapper objectMapper = new ObjectMapper();

  /**
   * Fetches 3-day weather forecast for a given city.
   *
   * @param city the city name
   * @return Weather forecast response
   * @throws WeatherDataUnavailableException when API and fallback both fail
   */
  @Override
  @Cacheable(value = "forecast", key = "#city", unless = "#result == null")
  public WeatherApiResponse fetch3DayForecast(String city) {
    if (properties.isOfflineMode()) {
      log.warn("OFFLINE mode enabled. Loading fallback data for city: {}", city);
      return loadFallbackResponse();
    }

    String url =
        String.format(
            "%s?q=%s&appid=%s&cnt=%d&units=metric",
            properties.getUrl(), city, properties.getKey(), properties.getCnt());

    log.info("Calling OpenWeatherMap API for city: {}, URL: {}", city, url);

    try {
      WebClient webClient = webClientBuilder.build();

      WeatherApiResponse response =
          webClient.get().uri(url).retrieve().bodyToMono(WeatherApiResponse.class).block();

      if (response == null || response.getList() == null) {
        log.warn("Null or empty weather API response for city: {}", city);
        throw new WeatherDataUnavailableException("Empty response from weather API");
      }

      log.info("Received valid forecast for city: {}", city);
      return response;

    } catch (Exception ex) {
      log.error("Error fetching weather data from API for city: {}. Using fallback.", city, ex);
      return loadFallbackResponse();
    }
  }

  /**
   * Loads local fallback JSON from the classpath.
   *
   * @return fallback forecast response
   * @throws WeatherDataUnavailableException if fallback file cannot be read
   */
  private WeatherApiResponse loadFallbackResponse() {
    try {
      return objectMapper.readValue(
          new ClassPathResource(properties.getFallbackPath()).getInputStream(),
          WeatherApiResponse.class);
    } catch (IOException e) {
      log.error("Unable to load fallback JSON from: {}", properties.getFallbackPath(), e);
      throw new WeatherDataUnavailableException("Failed to load fallback weather data", e);
    }
  }
}
