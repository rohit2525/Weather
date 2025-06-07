package org.example.weather.service.impl;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import java.util.List;
import org.example.weather.model.api.WeatherApiResponse;
import org.example.weather.util.ForecastEvaluator;
import org.example.weather.util.OpenWeatherApiClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WeatherForecastServiceImplTest {

  @Mock private OpenWeatherApiClient weatherApiClient;

  @Mock private ForecastEvaluator evaluator;

  @InjectMocks private WeatherForecastServiceImpl weatherForecastService;

  @Test
  void getForecast_ShouldCallMethodsCorrectly_WhenValidCityProvided() {
    // Given
    String city = "Mumbai";
    WeatherApiResponse apiResponse = new WeatherApiResponse(); // Mock API response object

    when(weatherApiClient.fetch3DayForecast(city)).thenReturn(apiResponse);
    when(evaluator.evaluateForecast(apiResponse)).thenReturn(List.of());

    // When
    weatherForecastService.getForecast(city);

    // Then
    verify(weatherApiClient).fetch3DayForecast(city);
    verify(evaluator).evaluateForecast(apiResponse);
  }

  @Test
  void getForecast_ShouldNotCallEvaluator_WhenApiClientFails() {
    // Given
    String city = "Mumbai";
    when(weatherApiClient.fetch3DayForecast(city)).thenThrow(new RuntimeException("API failed"));

    // When & Then
    assertThatThrownBy(() -> weatherForecastService.getForecast(city))
        .isInstanceOf(RuntimeException.class)
        .hasMessage("API failed");

    verify(weatherApiClient).fetch3DayForecast(city);
    verify(evaluator, never()).evaluateForecast(any());
  }
}
