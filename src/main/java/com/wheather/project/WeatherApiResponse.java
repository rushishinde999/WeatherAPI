package com.wheather.project;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class WeatherApiResponse {

    @JsonProperty("current_weather")
    private CurrentWeather currentWeather;

    private Daily daily;

    @Data
    public static class CurrentWeather {
        private double temperature;
    }

    @Data
    public static class Daily {
        @JsonProperty("temperature_2m_max")
        private List<Double> temperature_2m_max;

        @JsonProperty("temperature_2m_min")
        private List<Double> temperature_2m_min;
    }
}
