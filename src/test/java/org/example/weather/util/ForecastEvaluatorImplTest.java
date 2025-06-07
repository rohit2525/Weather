package org.example.weather.util;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import org.example.weather.model.ForecastResponse;
import org.example.weather.model.api.WeatherApiResponse;
import org.example.weather.model.api.WeatherApiResponse.ForecastItem;
import org.example.weather.model.api.WeatherApiResponse.Main;
import org.example.weather.model.api.WeatherApiResponse.Weather;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class ForecastEvaluatorTest {

  @Mock private ForecastEvaluator evaluator;

  @Test
  void shouldEvaluateForecastWithRainSuggestion() {
    // Prepare forecast item
    ForecastItem forecastItem = new ForecastItem();
    forecastItem.setDtTxt("2025-06-07 09:00:00");

    Main main = new Main();
    main.setTemp(25.5);
    main.setTempMax(28.0);
    main.setTempMin(22.0);
    forecastItem.setMain(main);

    Weather rain = new Weather();
    rain.setMain("Rain");
    rain.setDescription("light rain");
    forecastItem.setWeather(List.of(rain));

    // Wrap into response
    WeatherApiResponse apiResponse = new WeatherApiResponse();
    apiResponse.setList(List.of(forecastItem));

    // Evaluate
    List<ForecastResponse> result = evaluator.evaluateForecast(apiResponse);

    // Validate
    assertEquals(1, result.size());
    ForecastResponse day = result.get(0);
    assertEquals(LocalDate.of(2025, 6, 7), day.getDate());
    assertEquals(28.0, day.getHighTemp());
    assertEquals(22.0, day.getLowTemp());
    assertTrue(day.getSuggestions().contains("Carry umbrella"));
  }

  @Test
  void shouldEvaluateMultipleForecastsPerDay() {
    ForecastItem morning = new ForecastItem();
    morning.setDtTxt("2025-06-08 06:00:00");
    Main main1 = new Main();
    main1.setTemp(23.0);
    main1.setTempMax(24.0);
    main1.setTempMin(22.0);
    morning.setMain(main1);
    morning.setWeather(List.of(new Weather()));

    ForecastItem afternoon = new ForecastItem();
    afternoon.setDtTxt("2025-06-08 15:00:00");
    Main main2 = new Main();
    main2.setTemp(30.0);
    main2.setTempMax(31.0);
    main2.setTempMin(28.0);
    afternoon.setMain(main2);
    afternoon.setWeather(List.of(new Weather()));

    WeatherApiResponse response = new WeatherApiResponse();
    response.setList(List.of(morning, afternoon));

    List<ForecastResponse> result = evaluator.evaluateForecast(response);

    assertEquals(1, result.size());
    ForecastResponse day = result.get(0);
    assertEquals(31.0, day.getHighTemp());
    assertEquals(22.0, day.getLowTemp());
  }
}
