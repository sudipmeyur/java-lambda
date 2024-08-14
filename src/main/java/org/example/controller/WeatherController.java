package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import jakarta.validation.Valid;

@RestController
@Slf4j
public class WeatherController {

    private final WeatherDataService weatherDataService;

    public WeatherController(WeatherDataService weatherDataService, RestTemplate restTemplate){
        this.weatherDataService = weatherDataService;
    }

    @RequestMapping(path = "/test", method = RequestMethod.GET)
    public ResponseEntity<WeatherData> weather() {

        log.info("Received Request for Weather Data");



        WeatherData weatherResponse = weatherDataService.processWeatherData(WeatherInput.builder().city("Kolkata").state("WB").countryCode("IN").build());
        try {
            String jsonResp = new ObjectMapper().writeValueAsString(weatherResponse);
            log.info("jsonResp = {}",jsonResp);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(weatherResponse);
    }
    @RequestMapping(path = "/weather", method = RequestMethod.POST)
    public ResponseEntity<WeatherData> weather(@RequestBody @Valid WeatherInput weatherInput) {

        log.info("Received Request for Weather Data");

        WeatherData weatherResponse = weatherDataService.processWeatherData(weatherInput);
        return ResponseEntity.ok(weatherResponse);
    }
}
