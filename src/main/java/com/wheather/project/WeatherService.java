package com.wheather.project;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private final RestTemplate restTemplate = new RestTemplate();

    public WeatherEntity getWeather(String zipcode) {

        // Step 1: Get Latitude and Longitude from Nominatim
        String geoUrl = "https://nominatim.openstreetmap.org/search?postalcode=" + zipcode + "&country=India&format=json";
        ResponseEntity<NominatimResponse[]> geoResponse = restTemplate.getForEntity(geoUrl, NominatimResponse[].class);

        if (geoResponse.getBody() == null || geoResponse.getBody().length == 0) {
            throw new RuntimeException("Invalid zipcode or location not found");
        }

        double latitude = Double.parseDouble(geoResponse.getBody()[0].getLat());
        double longitude = Double.parseDouble(geoResponse.getBody()[0].getLon());

        // Step 2: Get Weather from Open-Meteo
        String weatherUrl = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude + "&longitude=" + longitude +
                "&current_weather=true&daily=temperature_2m_max,temperature_2m_min&timezone=auto";

        WeatherApiResponse weatherResponse = restTemplate.getForObject(weatherUrl, WeatherApiResponse.class);

        if (weatherResponse == null || weatherResponse.getCurrentWeather() == null) {
            throw new RuntimeException("Weather information not found");
        }

        // Step 3: Prepare the final response
        WeatherEntity response = new WeatherEntity();
        response.setZipcode(zipcode);
        response.setLatitude(latitude);
        response.setLongitude(longitude);
        response.setCurrentTemperature(weatherResponse.getCurrentWeather().getTemperature());
        response.setMinTemperature(weatherResponse.getDaily().getTemperature_2m_min().get(0));
        response.setMaxTemperature(weatherResponse.getDaily().getTemperature_2m_max().get(0));

        return response;
    }
}

