package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.domain.WeatherData;
import org.example.input.model.WeatherInput;
import org.example.service.WeatherDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import jakarta.validation.Valid;

@RestController
@Slf4j
public class WeatherController {

    private final WeatherDataService weatherDataService;

    public WeatherController(WeatherDataService weatherDataService, RestTemplate restTemplate){
        this.weatherDataService = weatherDataService;
    }

    @RequestMapping(path = "/weather", method = RequestMethod.POST)
    public ResponseEntity<WeatherData> weather(@RequestBody @Valid WeatherInput weatherInput) {

        log.info("Received Request for Weather Data");

        WeatherData weatherResponse = weatherDataService.processWeatherData(weatherInput);
        return ResponseEntity.ok(weatherResponse);
    }
}
