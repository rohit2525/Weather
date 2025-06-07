package org.example.weather.model.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/** Wrapper class for the JSON response returned by OpenWeatherMap API. */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherApiResponse {

  /** Response code from API */
  private String cod;

  /** Message from API */
  private int message;

  /** Count of forecast items */
  private int cnt;

  /** City-level metadata (optional usage). */
  private City city;

  /** List of weather forecasts in 3-hour intervals. */
  @JsonProperty("list")
  private List<ForecastItem> list;

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class City {
    private int id;
    private String name;
    private String country;
    private int population;
    private int timezone;
    private long sunrise;
    private long sunset;
    private Coord coord;
  }

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Coord {
    private double lat;
    private double lon;
  }

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class ForecastItem {

    /** Unix timestamp */
    private long dt;

    @JsonProperty("dt_txt")
    private String dtTxt;

    private Main main;

    private List<Weather> weather;

    private Wind wind;

    private Clouds clouds;

    private Sys sys;

    /** Visibility in meters */
    private int visibility;

    /** Probability of precipitation */
    private double pop;

    /** Rain data (optional) */
    private Rain rain;

    /** Snow data (optional) */
    private Snow snow;
  }

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Main {
    private double temp;

    @JsonProperty("feels_like")
    private double feelsLike;

    @JsonProperty("temp_min")
    private double tempMin;

    @JsonProperty("temp_max")
    private double tempMax;

    private int pressure;

    @JsonProperty("sea_level")
    private int seaLevel;

    @JsonProperty("grnd_level")
    private int grndLevel;

    private int humidity;

    @JsonProperty("temp_kf")
    private double tempKf;
  }

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Weather {
    private int id;
    private String main;
    private String description;
    private String icon;
  }

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Wind {
    private double speed;
    private int deg;
    private double gust;
  }

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Clouds {
    private int all;
  }

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Sys {
    private String pod;
  }

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Rain {
    @JsonProperty("3h")
    private double threeHour;
  }

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Snow {
    @JsonProperty("3h")
    private double threeHour;
  }
}