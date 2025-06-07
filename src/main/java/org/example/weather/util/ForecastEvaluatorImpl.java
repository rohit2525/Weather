package org.example.weather.util;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import org.example.weather.model.ForecastResponse;
import org.example.weather.model.api.WeatherApiResponse;
import org.springframework.stereotype.Component;

@Component
public class ForecastEvaluatorImpl implements ForecastEvaluator {

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public List<ForecastResponse> evaluateForecast(WeatherApiResponse apiResponse) {
        Map<LocalDate, ForecastResponse> dailySummary = new LinkedHashMap<>();

    apiResponse
        .getList()
        .forEach(
            item -> {
              LocalDate date =
                  LocalDateTime.parse(item.getDtTxt(), DATE_TIME_FORMATTER).toLocalDate();

              ForecastResponse forecast = dailySummary.computeIfAbsent(date, ForecastResponse::new);

              double temp = item.getMain().getTemp();
              forecast.setHighTemp(Math.max(forecast.getHighTemp(), temp));
              forecast.setLowTemp(Math.min(forecast.getLowTemp(), temp));

              if (temp > 40) forecast.getSuggestions().add("Use sunscreen lotion");
              if (item.getWeather().stream().anyMatch(w -> w.getMain().equalsIgnoreCase("Rain")))
                forecast.getSuggestions().add("Carry umbrella");
              if (item.getWeather().stream()
                  .anyMatch(w -> w.getMain().equalsIgnoreCase("Thunderstorm")))
                forecast.getSuggestions().add("Don't step out! A Storm is brewing!");
              if (item.getWind().getSpeed() > 10)
                forecast.getSuggestions().add("It's too windy, watch out!");
            });

        return new ArrayList<>(dailySummary.values());
    }
}