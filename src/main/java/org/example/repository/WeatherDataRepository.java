package org.example.repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.WeatherData;
import org.example.exception.WeatherException;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;

@Repository
@AllArgsConstructor
@Slf4j
public class WeatherDataRepository {

    private DynamoDbTable<WeatherData> weatherDataTable;

    public WeatherData create(WeatherData weatherData) {
        try {
            weatherDataTable.putItem(weatherData);
        }catch (Exception e){
            log.error("Exception in saving weather data ex = {}",e);
            throw new WeatherException("Exception in saving weather data",e);
        }
        return weatherData;
    }

    public WeatherData get(String weatherDataId){
        return weatherDataTable.getItem(Key.builder().partitionValue(weatherDataId).build());
    }
}
