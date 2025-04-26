package com.wheather.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/{zipcode}")
    public WeatherEntity getWeatherByZipcode(@PathVariable("zipcode") String zipcode) {
        return weatherService.getWeather(zipcode);
    }
}

