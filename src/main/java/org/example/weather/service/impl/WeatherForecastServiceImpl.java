package org.example.weather.service.impl;

import org.example.weather.model.ForecastResponse;
import org.example.weather.service.WeatherForecastService;
import lombok.RequiredArgsConstructor;
import org.example.weather.util.ForecastEvaluator;
import org.example.weather.util.OpenWeatherApiClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherForecastServiceImpl implements WeatherForecastService {

  private final OpenWeatherApiClient weatherApiClient;
  private final ForecastEvaluator evaluator;

  @Override
  public List<ForecastResponse> getForecast(String city) {
    var rawForecast = weatherApiClient.fetch3DayForecast(city);
    return evaluator.evaluateForecast(rawForecast);
  }
}
