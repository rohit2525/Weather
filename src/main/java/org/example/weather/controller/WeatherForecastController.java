package org.example.weather.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.weather.annotation.StandardWeatherResponses;
import org.example.weather.model.ForecastResponse;
import org.example.weather.service.WeatherForecastService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/weather")
@RequiredArgsConstructor
@Validated
@Tag(name = "Weather Forecast")
public class WeatherForecastController {

  private final WeatherForecastService forecastService;

  @GetMapping("/forecast")
  @Operation(summary = "Get 3-day weather forecast")
  @StandardWeatherResponses
  public List<ForecastResponse> getForecast(
      @RequestParam @NotBlank @Size(min = 2, max = 50) String city) {
    return forecastService.getForecast(city.trim());
  }
}
