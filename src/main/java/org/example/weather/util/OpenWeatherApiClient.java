package org.example.weather.util;


import org.example.weather.model.api.WeatherApiResponse;

public interface OpenWeatherApiClient {
  WeatherApiResponse fetch3DayForecast(String city);
}
