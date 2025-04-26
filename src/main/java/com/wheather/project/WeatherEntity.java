package com.wheather.project;

import lombok.Data;

@Data
public class WeatherEntity {
 
	 private String zipcode;
	    private double latitude;
	    private double longitude;
	    private double currentTemperature;
	    private double minTemperature;
	    private double maxTemperature;
   
}
