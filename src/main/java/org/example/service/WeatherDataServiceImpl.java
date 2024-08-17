package org.example.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.example.adaptor.WeatherAdaptor;
import org.example.domain.WeatherData;
import org.example.exception.WeatherException;
import org.example.input.model.WeatherInput;
import org.example.repository.WeatherDataRepository;
import org.example.transformer.WeatherTransformer;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
public class WeatherDataServiceImpl implements WeatherDataService{

    private final WeatherDataRepository weatherDataRepository;
    private final WeatherAdaptor weatherAdaptor;
    private final WeatherTransformer weatherTransformer;

    public WeatherDataServiceImpl(WeatherDataRepository weatherDataRepository, WeatherAdaptor weatherAdaptor, WeatherTransformer weatherTransformer) {
        this.weatherDataRepository = weatherDataRepository;
        this.weatherAdaptor = weatherAdaptor;
        this.weatherTransformer = weatherTransformer;
    }

    @Override
    public WeatherData processWeatherData(WeatherInput input) throws WeatherException {

        WeatherData weatherData = null;
        JsonNode weatherJson = null;
        JsonNode geoJson =weatherAdaptor.getGeoData(input.getCity(),input.getState(), input.getCountryCode());

        JsonNode geoLocData = extractGeoLocData(geoJson);

        if(geoLocData != null){
            double lat = geoLocData.get("lat").asDouble();
            double lon = geoLocData.get("lon").asDouble();
            weatherJson = weatherAdaptor.getWeatherData(lat,lon);
        }
        if(weatherJson != null){
            weatherData = weatherTransformer.transformToWeatherData(weatherJson);
            weatherDataRepository.create(weatherData);
            weatherData = weatherDataRepository.get(weatherData.getId());
        }
        return weatherData;
    }

    private JsonNode extractGeoLocData(JsonNode geoData) {
        JsonNode geoLocData = null;
        if(geoData==null || !geoData.isArray() ){
            return null;
        }
        Iterator<JsonNode> iterator = geoData.iterator();
        if(iterator.hasNext()){
            JsonNode jsonNode = iterator.next();

            if(jsonNode.hasNonNull("lat") && jsonNode.get("lat").isDouble()
                    && jsonNode.hasNonNull("lon") && jsonNode.get("lon").isDouble()){
                geoLocData = jsonNode;
            }
        }
       return geoLocData;
    }
}
