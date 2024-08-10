package org.example.validation;

import org.apache.commons.lang3.StringUtils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class CountryCodeValidator implements ConstraintValidator<ValidIsoCountry,String> {

    private final List<String> validIsoCountryCodes = Arrays.asList(Locale.getISOCountries());
    @Override
    public boolean isValid(String countryCode, ConstraintValidatorContext constraintValidatorContext) {
        
        return StringUtils.isBlank(countryCode) || validIsoCountryCodes.contains(countryCode);
    }
}
