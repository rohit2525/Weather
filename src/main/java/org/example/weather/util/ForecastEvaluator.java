package org.example.weather.util;

import org.example.weather.model.ForecastResponse;
import org.example.weather.model.api.WeatherApiResponse;

import java.util.List;

public interface ForecastEvaluator {
  List<ForecastResponse> evaluateForecast(WeatherApiResponse apiResponse);
}
