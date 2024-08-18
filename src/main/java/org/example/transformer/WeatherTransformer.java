package org.example.transformer;

import com.fasterxml.jackson.databind.JsonNode;
import org.example.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class WeatherTransformer {

    public WeatherData transformToWeatherData(JsonNode weatherJson){
        return WeatherData.builder()
                .weather(buildWeather(weatherJson))
                .base(validString(weatherJson.get("base")))
                .main(buildMain(weatherJson))
                .visibility(validInteger(weatherJson.get("visibility")))
                .wind(buildWind(weatherJson))
                .clouds(buildClouds(weatherJson))
                .dt(validInteger(weatherJson.get("dt")))
                .sys(buildSys(weatherJson))
                .timezone(validInteger(weatherJson.get("timezone")))
                .name(validString(weatherJson.get("name")))
                .cod(validInteger(weatherJson.get("cod")))
                .id(UUID.randomUUID().toString())
                .build();
    }

    private Sys buildSys(JsonNode weatherJson) {
        Sys sys = null;
        if(weatherJson.hasNonNull("sys")){
            JsonNode sysJson = weatherJson.get("sys");

            sys = Sys.builder()
                    .country(validString(sysJson.get("country")))
                    .sunrise(validInteger(sysJson.get("sunrise")))
                    .sunset(validInteger(sysJson.get("sunset")))
                    .build();
        }
        return sys;
    }

    private Clouds buildClouds(JsonNode weatherJson) {
        Clouds clouds = null;
        if(weatherJson.hasNonNull("clouds")){
            JsonNode cloudsJson = weatherJson.get("clouds");

            clouds = Clouds.builder()
                    .all(validInteger(cloudsJson.get("speed")))
                    .build();
        }
        return clouds;
    }

    private Wind buildWind(JsonNode weatherJson) {

        Wind wind = null;
        if(weatherJson.hasNonNull("wind")){
            JsonNode windJson = weatherJson.get("wind");

            wind = Wind.builder()
                    .speed(validDouble(windJson.get("speed")))
                    .deg(validInteger(windJson.get("deg")))
                    .gust(validDouble(windJson.get("gust")))
                    .build();
        }
        return wind;
    }

    private WeatherMain buildMain(JsonNode weatherJson) {
        WeatherMain weatherMain = null;
        if(weatherJson.hasNonNull("main")){
            JsonNode main = weatherJson.get("main");

            weatherMain = WeatherMain.builder()
                    .temp(validDouble(main.get("temp")))
                    .feelsLike(validDouble(main.get("feels_like")))
                    .tempMin(validDouble(main.get("temp_min")))
                    .tempMax(validDouble(main.get("temp_max")))
                    .pressure(validInteger(main.get("pressure")))
                    .humidity(validInteger(main.get("humidity")))
                    .seaLevel(validInteger(main.get("sea_level")))
                    .grndLevel(validInteger(main.get("grnd_level")))
                    .build();
        }
        return weatherMain;
    }



    private List<Weather> buildWeather(JsonNode weatherJson) {

        List<Weather> weatherList = new ArrayList<>();
        if(weatherJson.hasNonNull("weather")
                && weatherJson.get("weather").isArray()){

            for (JsonNode wthr : weatherJson.get("weather")) {
                Weather weather =
                        Weather.builder()
                                .main(validString(wthr.get("main")))
                                .description(validString(wthr.get("description")))
                                .build();
                weatherList.add(weather);
            }
        }
        return weatherList;
    }

    private Double validDouble(JsonNode temp) {
        if(temp == null || !temp.isDouble()){
            return 0d;
        }
        return temp.asDouble();
    }
    private Integer validInteger(JsonNode temp) {
        if(temp == null || !temp.isInt()){
            return 0;
        }
        return temp.asInt();
    }
    private String validString(JsonNode temp) {
        if(temp == null || !temp.isTextual()){
            return "";
        }
        return temp.asText();
    }
}
