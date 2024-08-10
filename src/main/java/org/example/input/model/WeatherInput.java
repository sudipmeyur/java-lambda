package org.example.input.model;

import lombok.Builder;
import lombok.Data;
import org.example.validation.ValidIsoCountry;

import jakarta.validation.constraints.NotBlank;

@Builder
@Data
public class WeatherInput {

    @NotBlank(message = "City can not be blank")
    private String city;
    private String state;

    @ValidIsoCountry(message = "Country code should be Iso country code")
    private String countryCode;
}
