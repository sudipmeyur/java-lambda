package org.example.service;

import org.example.domain.WeatherData;
import org.example.exception.WeatherException;
import org.example.input.model.WeatherInput;

public interface WeatherDataService {

    public WeatherData processWeatherData(WeatherInput input) throws WeatherException;
}
