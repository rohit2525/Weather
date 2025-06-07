package org.example.weather.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.weather.config.WeatherAppProperties;
import org.example.weather.model.api.WeatherApiResponse;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
@Slf4j
public class OpenWeatherApiClientImpl implements OpenWeatherApiClient {

  private final WebClient webClient;
  private final WeatherAppProperties properties;
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public WeatherApiResponse fetch3DayForecast(String city) {
    if (properties.isOfflineMode()) {
      log.warn(
          "OFFLINE mode is enabled. Loading mock response from: {}", properties.getFallbackPath());
      return loadFallbackResponse();
    }

    String url =
        String.format(
            "%s?q=%s&appid=%s&cnt=%d&units=metric",
            properties.getUrl(), city, properties.getKey(), properties.getCnt());

    try {
      return webClient.get().uri(url).retrieve().bodyToMono(WeatherApiResponse.class).block();
    } catch (Exception e) {
      log.error("Failed to fetch forecast from API. Switching to offline mode.", e);
      return loadFallbackResponse();
    }
  }

  private WeatherApiResponse loadFallbackResponse() {
    try {
      return objectMapper.readValue(
          new ClassPathResource("mock-forecast.json").getInputStream(), WeatherApiResponse.class);
    } catch (IOException e) {
      throw new RuntimeException("Failed to load mock data", e);
    }
  }
}
