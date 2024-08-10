package org.example.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class AppConfig {

//    @Bean
//    public RestTemplate restTemplate() {
//        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
//        httpRequestFactory.setConnectTimeout(30000);
//        httpRequestFactory.setConnectionRequestTimeout(30000);
//
//        return new RestTemplate(httpRequestFactory);
//    }
    //RestTemplateBuilder builder
   @Bean
   public RestTemplate restTemplate(RestTemplateBuilder builder) {
        builder.setReadTimeout(Duration.ofSeconds(30));
        builder.setConnectTimeout(Duration.ofSeconds(30));

        return builder.build();
    }

}
