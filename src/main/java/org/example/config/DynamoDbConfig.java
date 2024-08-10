package org.example.config;


import org.example.domain.WeatherData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.http.ExecutableHttpRequest;
import software.amazon.awssdk.http.HttpExecuteRequest;
import software.amazon.awssdk.http.SdkHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;

@Configuration
public class DynamoDbConfig {

    @Bean
    public DynamoDbClient dynamoDbClient(){

        DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
//                .endpointOverride(URI.create("https://us-east-1.console.aws.amazon.com"))
//                // The region is meaningless for local DynamoDb but required for client builder validation
//                .region(Region.US_EAST_1)
//
//                .credentialsProvider(StaticCredentialsProvider.create(
//                        AwsBasicCredentials.create("AKIAXRFLRWY7RJBX4WGB", "yaQEU8+7aBlm1HkFwpkl26nii9UUOw3zx/hYd3yY")))
                .build();

        return dynamoDbClient;
    }

    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient(){
        return DynamoDbEnhancedClient.builder()

                .dynamoDbClient(dynamoDbClient())
                .build();
    }

    @Bean
    public DynamoDbTable<WeatherData> weatherDataTable(){
        return  dynamoDbEnhancedClient().table(WeatherData.tableName(), TableSchema.fromBean(WeatherData.class));
    }
}
