package org.example.adaptor;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.exception.WeatherException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Slf4j
public class WeatherAdaptor {
    private final RestTemplate restTemplate;

    public WeatherAdaptor(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${weather.app.id}")
    private String appId;
    
    public JsonNode getWeatherData(double lat,double lon) throws WeatherException {
        JsonNode weatherData = null;
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://api.openweathermap.org/data/2.5/weather")
                    .queryParam("lat", lat)
                    .queryParam("lon", lon)
                    .queryParam("appid",appId);
            ResponseEntity<JsonNode> response = restTemplate.getForEntity(builder.build().toUri(), JsonNode.class);

            if(response.getStatusCode() == HttpStatus.OK){
                weatherData = response.getBody();
            }
        } catch (Exception e) {
            log.error("Exception in getting weather data ex = {}",e);
            throw new WeatherException("Exception in getting weather data",e);
        }
        return weatherData;
    }

    public JsonNode getGeoData(String city, String state, String countryCode) throws WeatherException {
        JsonNode geoData = null;


        String q = city;

        if(StringUtils.isNotBlank(countryCode)){
            if(StringUtils.isNotBlank(state)){
                q = String.join(",",q,state);
            }
            q = String.join(",",q,countryCode);
        }


        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://api.openweathermap.org/geo/1.0/direct")
                    .queryParam("q", q)
                    .queryParam("appid",appId);
            ResponseEntity<JsonNode> response = restTemplate.getForEntity(builder.build().toUri(), JsonNode.class);

            if(response.getStatusCode() == HttpStatus.OK){
                geoData = response.getBody();
            }
        } catch (Exception e) {
            log.error("Exception in getting geo data ex = {}",e);
            throw new WeatherException("Exception in getting geo data",e);
        }
        return geoData;
    }
}
