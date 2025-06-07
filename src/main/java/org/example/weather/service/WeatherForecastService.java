package org.example.weather.service;

import org.example.weather.model.ForecastResponse;
import java.util.List;

public interface WeatherForecastService {
  List<ForecastResponse> getForecast(String city);
}
