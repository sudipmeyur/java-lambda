package org.example.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CountryCodeValidator.class)
public @interface ValidIsoCountry {

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public String message() default "Not a valid Iso Country Code";
}
