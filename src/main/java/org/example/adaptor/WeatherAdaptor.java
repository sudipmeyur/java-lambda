package org.example.adaptor;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.constant.WeatherConstant;
import org.example.exception.WeatherException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static org.example.constant.WeatherConstant.*;

@Service
@Slf4j
public class WeatherAdaptor {
    private final RestTemplate restTemplate;

    public WeatherAdaptor(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${weather.app.id}")
    private String appId;
    @Value("${weather.app.base.url}")
    private String weatherBaseUrl;
    @Value("${weather.app.geo.url}")
    private String weatherGeoUrl;
    @Value("${weather.app.weather.url}")
    private String weatherDataUrl;

    
    public JsonNode getWeatherData(double lat,double lon) throws WeatherException {
        JsonNode weatherData = null;
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(String.join(FORWARD_SLASH,weatherBaseUrl,weatherDataUrl))
                    .queryParam(LAT, lat)
                    .queryParam(LON, lon)
                    .queryParam(APPID,appId);
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
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(String.join(FORWARD_SLASH,weatherBaseUrl,weatherGeoUrl))
                    .queryParam(Q, q)
                    .queryParam(APPID,appId);
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
